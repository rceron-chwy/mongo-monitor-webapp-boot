package com.mongodash.monitor.type;

import org.apache.http.auth.InvalidCredentialsException;

import com.mongodash.Config;
import com.mongodash.converter.ServerStatusConverter;
import com.mongodash.event.MongoEventBus;
import com.mongodash.event.ServerStatusEvent;
import com.mongodash.exception.MonitorException;
import com.mongodash.model.Server;
import com.mongodash.model.ServerStatus;
import com.mongodash.monitor.AbstractMonitor;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MonitorTcp extends AbstractMonitor {
	
	private ServerStatus previous = null;
	private ServerStatus current = null;
	private Server server;
	private DB db;
	private DBCollection stasCollection;
	
	public MonitorTcp(Server server, DBCollection stasCollection) {
		this.server = server;
		this.stasCollection = stasCollection;
	}
	
	
	@Override
	public void execute(int seconds) throws MonitorException {	
		
		try {
			
			if(db == null) {
				MongoClient mongoClient = new MongoClient(server.getHostname() + ":" + server.getPort());
				db = mongoClient.getDB(Config.MONGO_COLLECTION.admin.name());
				if(server.getUsername() != null && server.getPassword() != null &&
						server.getUsername().length() > 0 && server.getPassword().length() > 0) {
					boolean isOk = db.authenticate(server.getUsername(), server.getPassword().toCharArray());
					if(!isOk) {
						throw new MonitorException(server, new InvalidCredentialsException());
					}
				}				
			}
			
			CommandResult result = db.command("serverStatus");
			if(previous == null) {
				previous = ServerStatusConverter.fromTCPDBObject(result);
			}
			else {
				current = ServerStatusConverter.fromTCPDBObject(result);
				ServerStatus diff = ServerStatusConverter.getDiff(previous, current, seconds);
				saveDiff(diff);
				updateServerLastUpdate(server);
				previous = current;
				MongoEventBus.post(new ServerStatusEvent(server, diff));
			}
		}
		catch(Exception e) {
			throw new MonitorException(server, e);
		}
	}

	@Override
	public void setServer(Server server) {
		this.server = server;
	}

	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public DBCollection getStatsCollection() {
		return stasCollection;
	}
}
