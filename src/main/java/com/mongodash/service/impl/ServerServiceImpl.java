package com.mongodash.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.http.protocol.BasicHttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodash.Config;
import com.mongodash.dao.ServerDao;
import com.mongodash.event.MongoEventBus;
import com.mongodash.event.ServerEvent;
import com.mongodash.model.BuildInfo;
import com.mongodash.model.DatabaseInfo;
import com.mongodash.model.HostInfo;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.model.Response;
import com.mongodash.model.Server;
import com.mongodash.model.ServerUpdateMethodType;
import com.mongodash.model.Server.Type;
import com.mongodash.monitor.AgentKeysHolder;
import com.mongodash.monitor.MongoMonitor;
import com.mongodash.monitor.type.MonitorHttp;
import com.mongodash.monitor.type.MonitorTcp;
import com.mongodash.service.MonitorService;
import com.mongodash.service.ServerService;
import com.mongodash.util.MongoDashClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@Service
public class ServerServiceImpl implements ServerService {

	private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);

	@Autowired
	private ServerDao serverDao;

	@Autowired
	MonitorService monitorService;

	@Autowired
	MongoDashClient mongoClient;
	
	@Autowired
	AgentKeysHolder keyHolder;	

	@Override
	public List<Server> list() {
		List<Server> servers = serverDao.list();
		if (servers != null) {
			for (Server server : servers) {
				server.setMonitorRunning(monitorService.isRunning(server.getName()));
			}
		}
		return servers;
	}

	@Override
	public Server getById(Integer id) {
		Server server = null;
		server = serverDao.getById(id);
		if (server != null) {
			server.setMonitorRunning(monitorService.isRunning(server.getName()));
		}
		return server;
	}

	@Override
	public void save(Server server) {
		serverDao.save(server);
		if(ServerUpdateMethodType.AGENT.equals(server.getMethod())) {
			keyHolder.loadKeys();
		}		
		MongoEventBus.post(new Notification(NotificationType.SERVER_CREATED, server.getName()), 
				new ServerEvent(NotificationType.SERVER_CREATED, server));
	}

	@Override
	public void update(Server server) {
		serverDao.update(server);
		if(ServerUpdateMethodType.AGENT.equals(server.getMethod())) {
			keyHolder.loadKeys();
		}
		MongoEventBus.post(new Notification(NotificationType.SERVER_UPDATED, server.getName()));
	}

	@Override
	public void remove(Server server) {

		if (server != null && monitorService.isRunning(server.getName())) {
			monitorService.remove(server.getName());
		}
		
		if(ServerUpdateMethodType.AGENT.equals(server.getMethod())) {
			keyHolder.loadKeys();
		}		

		serverDao.remove(server);
		MongoEventBus.post(new Notification(NotificationType.SERVER_REMOVED, server.getName()), 
				new ServerEvent(NotificationType.SERVER_REMOVED, server));
	}

	@Override
	public void removeById(Integer id) {
		Server server = getById(id);
		if(server != null) {
			remove(server);
		}
	}

	@Override
	public Response startMonitor(Integer serverId) {

		Server server = getById(serverId);
		Response response = new Response();

		if (!monitorService.isRunning(server.getName())) {
			DB localDb = mongoClient.getDashDB();
			DBCollection dbColl = localDb.getCollection(Config.MONGO_COLLECTION.serverStats.name());
			logger.info("Starting monitor for for server " + server.getName());
			if (ServerUpdateMethodType.TCP.name().equals(server.getMethod())) {
				MongoMonitor monitor = new MongoMonitor(server.getInterval(), new MonitorTcp(server, dbColl));
				monitorService.execute(monitor);
				MongoEventBus.post(new Notification(NotificationType.MONITOR_STARTED, server.getName()));
			} else if (ServerUpdateMethodType.HTTP.name().equals(server.getMethod())) {
				MongoMonitor monitor = new MongoMonitor(server.getInterval(), new MonitorHttp(server, dbColl, new BasicHttpContext()));
				monitorService.execute(monitor);
				MongoEventBus.post(new Notification(NotificationType.MONITOR_STARTED, server.getName()));
			} else {
				response.setStatus("ERROR");
				response.setErrorMessage("Cannot start monitor for server with update method type agent");
			}
		} else {
			logger.info("Monitor is already running for server " + server.getName());
			response.setStatus("INFO");
			response.setErrorMessage("Monitor already running for this server");
		}

		return new Response();
	}

	@Override
	public Response stopMonitor(Integer serverId) {

		Server server = getById(serverId);

		if (monitorService.isRunning(server.getName())) {
			logger.info("Stopping monitor for for server " + server.getName());
			monitorService.remove(server.getName());
			MongoEventBus.post(new Notification(NotificationType.MONITOR_STOPPED, server.getName()));
		} else {
			logger.info("Monitor is not running for server " + server.getName());
			Response response = new Response();
			response.setStatus("INFO");
			response.setErrorMessage("Monitor not running for this server");
		}

		return new Response();
	}

	@Override
	public void saveHostInfo(HostInfo hostInfo) {
		serverDao.saveHostInfo(hostInfo);
	}

	@Override
	public void saveBuildInfo(BuildInfo buildInfo) {
		serverDao.saveBuildInfo(buildInfo);
	}

	@Override
	public void saveDatabaseInfo(DatabaseInfo databaseInfo) {
		serverDao.saveDatabaseInfo(databaseInfo);
	}

	@Override
	public HostInfo getHostInfo(Integer serverId) {
		return serverDao.getHostInfo(serverId);
	}

	@Override
	public BuildInfo getBuildInfo(Integer serverId) {
		return serverDao.getBuildInfo(serverId);
	}

	@Override
	public DatabaseInfo getDatabaseInfo(Integer serverId) {
		return serverDao.getDatabaseInfo(serverId);
	}

	@Override
	public void removeServerInfo(Integer serverId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInstanceInfo(Integer serverId, String process, String version, Date uptime) {
		serverDao.updateInstanceInfo(serverId, process, version, uptime);
	}

	@Override
	public void updateReplInfo(Integer serverId, Type serverType, String replName) {
		serverDao.updateReplInfo(serverId, serverType, replName);
		
	}

}
