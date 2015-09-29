package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.StatsDao;
import com.mongodash.dao.mapper.ServerStatusDBObjectMapper;
import com.mongodash.model.ServerStatus;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Repository
public class StatsDaoImpl extends BaseMongoDao implements StatsDao {

	@Autowired
	private ServerStatusDBObjectMapper objectMapper;
	
	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.serverStats.name();
	}


	@Override
	public void saveServerStats(ServerStatus serverStatus) {
		save(serverStatus, objectMapper);
	}

	@Override
	public List<ServerStatus> getLastServerStatus(Integer serverId, int limit) {
		DBObject query = new BasicDBObject("server", serverId);
		DBObject sort = new BasicDBObject(COMMON_FIELDS._id.name(), -1);
		return find(objectMapper, query, sort, limit);
	}

}
