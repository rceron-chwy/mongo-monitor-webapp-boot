package com.mongodash.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.ServerDao;
import com.mongodash.dao.mapper.BuildInfoDBObjectMapper;
import com.mongodash.dao.mapper.DatabaseInfoDBObjectMapper;
import com.mongodash.dao.mapper.HostInfoDBObjectMapper;
import com.mongodash.dao.mapper.ServerDBObjectMapper;
import com.mongodash.model.BuildInfo;
import com.mongodash.model.DatabaseInfo;
import com.mongodash.model.HostInfo;
import com.mongodash.model.Server;
import com.mongodash.model.Server.Type;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Repository
public class ServerDaoImpl extends BaseMongoDao implements ServerDao {

	@Autowired
	private ServerDBObjectMapper serverMapper;
	
	@Autowired
	private HostInfoDBObjectMapper hostInfoMapper;
	
	@Autowired
	private BuildInfoDBObjectMapper buildInfoMapper;
	
	@Autowired
	private DatabaseInfoDBObjectMapper databaseInfoMapper;

	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.servers.name();
	}

	@Override
	public List<Server> list() {
		return find(serverMapper, new BasicDBObject(Config.COMMON_FIELDS.name.name(), 1));
	}

	@Override
	public Server getById(Integer id) {
		return findOne(new BasicDBObject("_id", id), serverMapper);
	}

	@Override
	public void save(Server server) {
		server.setId(getNextVal());
		save(server, serverMapper);
	}

	@Override
	public void update(Server server) {
		update(new BasicDBObject(Config.COMMON_FIELDS._id.name(), server.getId()), server, serverMapper);
	}

	@Override
	public void remove(Server server) {
		removeById(server.getId());
	}

	@Override
	public void removeById(Integer id) {
		DBCollection dbColl = getCollection();
		dbColl.remove(new BasicDBObject(Config.COMMON_FIELDS._id.name(), id));
	}

	@Override
	public void saveHostInfo(HostInfo hostInfo) {
		DBCollection col = getCollection().getDB().getCollection(Config.MONGO_COLLECTION.serverInfo.name());
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), hostInfo.getId());
		DBObject newfields = new BasicDBObject();
		newfields.put(COMMON_FIELDS.updated.name(), new Date());
		newfields.put("hostInfo", hostInfoMapper.mapObject(hostInfo));
		DBObject update = new BasicDBObject("$set", newfields);
		col.update(query, update, true, false);
		// update(col, query, hostInfo, new HostInfoDBObjectMapper(), true,
		// false);
	}

	@Override
	public void saveBuildInfo(BuildInfo buildInfo) {
		DBCollection col = getCollection().getDB().getCollection(Config.MONGO_COLLECTION.serverInfo.name());
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), buildInfo.getId());
		DBObject newfields = new BasicDBObject();
		newfields.put(COMMON_FIELDS.updated.name(), new Date());
		newfields.put("buildInfo", buildInfoMapper.mapObject(buildInfo));
		DBObject update = new BasicDBObject("$set", newfields);
		col.update(query, update, true, false);
	}

	@Override
	public void saveDatabaseInfo(DatabaseInfo databaseInfo) {
		DBCollection col = getCollection().getDB().getCollection(Config.MONGO_COLLECTION.serverInfo.name());
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), databaseInfo.getId());
		DBObject newfields = new BasicDBObject();
		newfields.put(COMMON_FIELDS.updated.name(), new Date());
		newfields.put("databaseInfo", databaseInfoMapper.mapObject(databaseInfo));
		DBObject update = new BasicDBObject("$set", newfields);
		col.update(query, update, true, false);
	}

	@Override
	public HostInfo getHostInfo(Integer serverId) {
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), serverId);
		DBCollection serverInfo = getCollection().getDB().getCollection(Config.MONGO_COLLECTION.serverInfo.name());
		DBObject si = serverInfo.findOne(query);
		BasicDBObject hInfo = (BasicDBObject) si.get("hostInfo");
		if(hInfo != null) {
			return hostInfoMapper.mapDBObject(hInfo);
		}
		return null;
	}

	@Override
	public BuildInfo getBuildInfo(Integer serverId) {
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), serverId);
		DBCollection serverInfo = getCollection().getDB().getCollection(Config.MONGO_COLLECTION.serverInfo.name());
		DBObject si = serverInfo.findOne(query);
		BasicDBObject bInfo = (BasicDBObject) si.get("buildInfo");
		if(bInfo != null) {
			return buildInfoMapper.mapDBObject(bInfo);
		}
		return null;
	}

	@Override
	public DatabaseInfo getDatabaseInfo(Integer serverId) {
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), serverId);
		DBCollection serverInfo = getCollection().getDB().getCollection(Config.MONGO_COLLECTION.serverInfo.name());
		DBObject si = serverInfo.findOne(query);
		BasicDBObject dbInfo = (BasicDBObject) si.get("databaseInfo");
		if(dbInfo != null) {
			return databaseInfoMapper.mapDBObject(dbInfo);
		}
		return null;
	}

	@Override
	public void updateInstanceInfo(Integer serverId, String process, String version, Date uptime) {
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), serverId);
		DBObject newvalues = new BasicDBObject();
		newvalues.put(Server.fields.process.name(), process);
		newvalues.put(Server.fields.version.name(), version);
		newvalues.put(Server.fields.uptime.name(), uptime);
		newvalues.put(COMMON_FIELDS.updated.name(), new Date());
		DBObject update = new BasicDBObject("$set", newvalues);
		getCollection().update(query, update, true, false);
	}

	@Override
	public void updateReplInfo(Integer serverId, Type serverType, String replName) {
		DBObject query = new BasicDBObject(COMMON_FIELDS._id.name(), serverId);
		DBObject newvalues = new BasicDBObject();
		newvalues.put(Server.fields.type.name(), serverType.name());
		if (replName != null) {
			newvalues.put(Server.fields.replName.name(), replName);
		}
		newvalues.put(COMMON_FIELDS.updated.name(), new Date());
		DBObject update = new BasicDBObject("$set", newvalues);
		getCollection().update(query, update, true, false);
	}
}