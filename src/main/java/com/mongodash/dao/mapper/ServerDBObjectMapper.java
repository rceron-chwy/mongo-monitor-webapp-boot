package com.mongodash.dao.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodash.Config;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.Server;
import com.mongodash.model.ServerUpdateMethodType;
import com.mongodash.util.PasswordUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class ServerDBObjectMapper implements DBObjectMapper<Server> {

	@Override
	public Server mapDBObject(BasicDBObject object) {
		Server server = new Server();
		server.setId(object.getInt(Config.COMMON_FIELDS._id.name()));
		server.setName(object.getString(Config.COMMON_FIELDS.name.name()));
		server.setHostname(object.getString(Server.fields.hostname.name()));
		server.setPort(object.getInt(Server.fields.port.name()));
		server.setInterval(object.getInt(Server.fields.sleep.name()));
		server.setRetain(object.getInt(Server.fields.retain.name()));
		server.setMethod(object.getString(Server.fields.method.name()));

		if (object.containsField(Server.fields.secure.name())) {
			server.setSecure(object.getBoolean(Server.fields.secure.name()));
		}

		if (object.containsField(Server.fields.username.name()) && object.containsField(Server.fields.password.name())) {
			server.setUsername(object.getString(Server.fields.username.name()));
			server.setPassword(PasswordUtil.decrypt(object.getString(Server.fields.password.name())));
		}

		if (object.containsField(Server.fields.key.name())) {
			server.setKey(object.getString(Server.fields.key.name()));
		}

		if (object.containsField(Server.fields.lastMonitorUpdate.name())) {
			server.setLastMonitorUpdate(object.getDate(Server.fields.lastMonitorUpdate.name()));
		}

		if (object.containsField(Server.fields.type.name())) {
			server.setType(object.getString(Server.fields.type.name()));
		}
		
		if (object.containsField(Server.fields.process.name())) {
			server.setProcess(object.getString(Server.fields.process.name()));
		}

		if (object.containsField(Server.fields.version.name())) {
			server.setVersion(object.getString(Server.fields.version.name()));
		}
		
		if (object.containsField(Server.fields.uptime.name())) {
			server.setUpTime(object.getDate(Server.fields.uptime.name()));
		}
		
		if (object.containsField(Server.fields.replName.name())) {
			server.setReplName(object.getString(Server.fields.replName.name()));
		}

		return server;
	}

	@Override
	public DBObject mapObject(Server object) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Config.COMMON_FIELDS._id.name(), object.getId());
		dbObject.put(Config.COMMON_FIELDS.name.name(), object.getName());
		dbObject.put(Server.fields.hostname.name(), object.getHostname());
		dbObject.put(Server.fields.port.name(), (object.getPort() == null) ? 27017 : object.getPort());
		dbObject.put(Server.fields.method.name(), object.getMethod());
		dbObject.put(Server.fields.sleep.name(), (object.getInterval() == null) ? 5 : object.getInterval());
		dbObject.put(Server.fields.secure.name(), object.isSecure());
		dbObject.put(Server.fields.retain.name(), (object.getRetain() == null) ? 10 : object.getRetain());

		if (StringUtils.hasText(object.getUsername()) && StringUtils.hasText(object.getPassword())) {
			dbObject.put(Server.fields.username.name(), object.getUsername());
			dbObject.put(Server.fields.password.name(), PasswordUtil.encrypt(object.getPassword()));
		}

		if (object.getMethod().equals(ServerUpdateMethodType.AGENT.name()) && StringUtils.hasText(object.getKey()))
			dbObject.put(Server.fields.key.name(), object.getKey());

		dbObject.put(Config.COMMON_FIELDS.updated.name(), new Date());
		return dbObject;
	}

}
