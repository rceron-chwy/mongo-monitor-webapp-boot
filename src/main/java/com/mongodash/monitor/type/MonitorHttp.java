package com.mongodash.monitor.type;

import http.client.wrapper.response.Response;

import org.apache.http.HttpStatus;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.protocol.HttpContext;

import com.mongodash.converter.ServerStatusConverter;
import com.mongodash.event.MongoEventBus;
import com.mongodash.event.ServerStatusEvent;
import com.mongodash.exception.MonitorException;
import com.mongodash.exception.ServerStatusException;
import com.mongodash.http.HttpClient;
import com.mongodash.model.Server;
import com.mongodash.model.ServerStatus;
import com.mongodash.monitor.AbstractMonitor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class MonitorHttp extends AbstractMonitor {

	private ServerStatus previous = null;
	private ServerStatus current = null;
	private Server server;
	private DBCollection statsCollection;
	private HttpClient httpClient;
	private HttpContext httpContext;


	public MonitorHttp(Server server, DBCollection statsCollection, HttpContext httpContext) {
		this.server = server;
		this.statsCollection = statsCollection;
		this.httpContext = httpContext;
	}

	public DB getDB() {
		return statsCollection.getDB();
	}

	@Override
	public void execute(int seconds) throws MonitorException {

		try {
			
			if(httpClient == null) {
				httpClient = new HttpClient(server, httpContext);
				if(server.getUsername() != null && server.getPassword() != null 
						&& !httpClient.isValidCredentials()) {
					throw new MonitorException(server, new InvalidCredentialsException());		
				}
			}
			
			Response response = httpClient.getServerStatus();
			if(response.getStatusCode() == HttpStatus.SC_OK) {
				DBObject result = (DBObject) JSON.parse(response.getOutput());
				if (previous == null) {
					previous = ServerStatusConverter.fromHTTPDBObject(result);
				} else {
					current = ServerStatusConverter.fromHTTPDBObject(result);
					ServerStatus diff = ServerStatusConverter.getDiff(previous, current, seconds);
					saveDiff(diff);
					updateServerLastUpdate(server);
					previous = current;
					MongoEventBus.post(new ServerStatusEvent(server, diff));
				}				
			}
			else {
				throw new RuntimeException("HttpCode was not 200");
			}
		} catch (RuntimeException e) {
			throw new MonitorException("Error found", server, e);
		} catch (ServerStatusException e) {
			throw new MonitorException(e.getMessage(), server, e);
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
		return statsCollection;
	}
}
