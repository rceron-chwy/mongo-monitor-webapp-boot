package com.mongodash.monitor;

import java.util.Date;

import com.mongodash.Config;
import com.mongodash.converter.ServerStatusConverter;
import com.mongodash.model.Server;
import com.mongodash.model.ServerStatus;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


public abstract class AbstractMonitor implements Monitor {
	
	public abstract DBCollection getStatsCollection();

	public void updateServerLastUpdate(Server server) {
		DB db = getStatsCollection().getDB();
		DBCollection serverColl = db.getCollection(Config.MONGO_COLLECTION.servers.name());
		DBObject lastUpdate = new BasicDBObject("lastMonitorUpdate", new Date());
		serverColl.update(new BasicDBObject("_id", server.getId()), new BasicDBObject("$set", lastUpdate));
	}
	
	
	public void saveDiff(ServerStatus diff) {
		DBObject d = ServerStatusConverter.toDBObject(diff);
		d.put("server", getServer().getId());
		getStatsCollection().save(d);
	}
}
