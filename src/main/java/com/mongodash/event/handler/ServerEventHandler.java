package com.mongodash.event.handler;

import http.client.wrapper.response.Response;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.mongodash.Config;
import com.mongodash.converter.BuildInfoConverter;
import com.mongodash.converter.DatabaseInfoConverter;
import com.mongodash.converter.HostInfoConverter;
import com.mongodash.event.MongoEventBus;
import com.mongodash.event.ServerEvent;
import com.mongodash.http.HttpClient;
import com.mongodash.model.BuildInfo;
import com.mongodash.model.DatabaseInfo;
import com.mongodash.model.HostInfo;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.model.Server;
import com.mongodash.model.ServerUpdateMethodType;
import com.mongodash.model.Server.Type;
import com.mongodash.service.MonitorService;
import com.mongodash.service.ServerService;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Component
public class ServerEventHandler {

	static final Logger logger = LoggerFactory.getLogger(ServerEventHandler.class);

	@Autowired
	private MonitorService monitorService;

	@Autowired
	private ServerService serverService;

	@PostConstruct
	private void subscribeToService() {
		MongoEventBus.register(this);
	}

	@Subscribe
	public void handleEvent(ServerEvent serverEvent) {

		if (serverEvent.getEventType() == NotificationType.SERVER_CREATED) {
			logger.debug("ServerEventHandler.handleEvent(): SERVER_CREATED");
			logger.debug(serverEvent.getServer().toString());
			if (ServerUpdateMethodType.valueOf(serverEvent.getServer().getMethod()) == ServerUpdateMethodType.HTTP) {
				collectHttpData(serverEvent.getServer());
			} else if (ServerUpdateMethodType.valueOf(serverEvent.getServer().getMethod()) == ServerUpdateMethodType.TCP) {
				collectTcpData(serverEvent.getServer());
			} else {
				// TODO
			}

		} else if (serverEvent.getEventType() == NotificationType.SERVER_UPDATED) {

		} else if (serverEvent.getEventType() == NotificationType.SERVER_REMOVED) {
			logger.debug("ServerEventHandler.handleEvent(): SERVER_REMOVED");
			serverService.removeServerInfo(serverEvent.getServer().getId());
		} else if (serverEvent.getEventType() == NotificationType.SERVER_DOWN) {
			monitorService.remove(serverEvent.getServer().getName());
		}

	}

	private void collectTcpData(Server server) {

		try {

			MongoClient mongoClient = new MongoClient(server.getHostname() + ":" + server.getPort());
			DB db = mongoClient.getDB(Config.MONGO_COLLECTION.admin.name());
			if (server.getUsername() != null && server.getPassword() != null && server.getUsername().length() > 0 && server.getPassword().length() > 0) {
				boolean isOk = db.authenticate(server.getUsername(), server.getPassword().toCharArray());
				if (!isOk) {
					logger.error("Authentication failed for server " + server.getName());
					return;
				}
			}

			CommandResult hostInfo = db.command("hostInfo");
			CommandResult buildInfo = db.command("buildInfo");
			CommandResult listDatabases = db.command("listDatabases");
			CommandResult serverStatus = db.command("serverStatus");
			CommandResult masterInfo = db.command("isMaster");
			mongoClient.close();

			if (hostInfo.getInt("ok") == 1) {
				HostInfo hi = HostInfoConverter.fromDBObject(hostInfo, true);
				hi.setId(server.getId());
				serverService.saveHostInfo(hi);
			}

			if (buildInfo.getInt("ok") == 1) {
				BuildInfo bi = BuildInfoConverter.fromDBObject(buildInfo);
				bi.setId(server.getId());
				serverService.saveBuildInfo(bi);
			}

			if (listDatabases.getInt("ok") == 1) {
				DatabaseInfo di = DatabaseInfoConverter.fromDBObject(listDatabases);
				di.setId(server.getId());
				serverService.saveDatabaseInfo(di);
			}

			if (serverStatus.getInt("ok") == 1) {
				// update type and version
				String version = serverStatus.getString("version");
				String type = serverStatus.getString("process");
				Integer uptime = serverStatus.getInt("uptime");
				Date uptimeDate = getUptimeDate(uptime);
				serverService.updateInstanceInfo(server.getId(), type, version, uptimeDate);
			}

			if (masterInfo.getInt("ok") == 1) {
				Type serverType = Type.standalone;
				String setName = null;
				if (masterInfo.containsField("setName")) {
					Boolean secondary = masterInfo.getBoolean("secondary");
					setName = masterInfo.getString("setName");
					if (secondary) {
						serverType = Type.secondary;
					} else {
						serverType = Type.master;
					}
				}
				serverService.updateReplInfo(server.getId(), serverType, setName);
			}

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage(), e.getCause());
		}
	}

	private void collectHttpData(Server server) {

		try {

			HttpClient httpClient = new HttpClient(server);
			Response binfo = httpClient.getBuildInfo();
			Response hInfo = httpClient.getHostInfo();
			Response dInfo = httpClient.getDatabaseInfo();
			Response sStatus = httpClient.getServerStatus();
			Response mInfo = httpClient.getIsMaster();

			if (binfo.getStatusCode() == HttpStatus.SC_OK) {
				BasicDBObject buildInfo = (BasicDBObject) JSON.parse(binfo.getOutput());
				BuildInfo bi = BuildInfoConverter.fromDBObject(buildInfo);
				bi.setId(server.getId());
				serverService.saveBuildInfo(bi);
			}

			if (hInfo.getStatusCode() == HttpStatus.SC_OK) {
				BasicDBObject hostInfo = (BasicDBObject) JSON.parse(hInfo.getOutput());
				HostInfo hi = HostInfoConverter.fromDBObject(hostInfo, true);
				hi.setId(server.getId());
				serverService.saveHostInfo(hi);
			}

			if (dInfo.getStatusCode() == HttpStatus.SC_OK) {
				BasicDBObject listDatabases = (BasicDBObject) JSON.parse(dInfo.getOutput());
				DatabaseInfo di = DatabaseInfoConverter.fromDBObject(listDatabases);
				di.setId(server.getId());
				serverService.saveDatabaseInfo(di);
			}

			if (sStatus.getStatusCode() == HttpStatus.SC_OK) {
				BasicDBObject serverStatus = (BasicDBObject) JSON.parse(sStatus.getOutput());
				String version = serverStatus.getString("version");
				String type = serverStatus.getString("process");
				Integer uptime = serverStatus.getInt("uptime");
				Date uptimeDate = getUptimeDate(uptime);
				serverService.updateInstanceInfo(server.getId(), type, version, uptimeDate);
			}

			if (mInfo.getStatusCode() == HttpStatus.SC_OK) {
				BasicDBObject isMaster = (BasicDBObject) JSON.parse(sStatus.getOutput());
				Type serverType = Type.standalone;
				String setName = null;
				if (isMaster.containsField("setName")) {
					Boolean secondary = isMaster.getBoolean("secondary");
					setName = isMaster.getString("setName");
					if (secondary) {
						serverType = Type.secondary;
					} else {
						serverType = Type.master;
					}
				}
				serverService.updateReplInfo(server.getId(), serverType, setName);
			}

		} catch (RuntimeException e) {
			logger.error("Error in ServerEventHandler", e);
			MongoEventBus.post(e, new Notification(NotificationType.SERVER_DOWN, server.getName()), new ServerEvent(NotificationType.SERVER_DOWN, server));
		}
	}

	private Date getUptimeDate(int seconds) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.SECOND, -1 * seconds);
		return cal.getTime();
	}
}
