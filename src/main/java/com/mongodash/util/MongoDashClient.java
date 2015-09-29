package com.mongodash.util;

import java.net.UnknownHostException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Component
public class MongoDashClient {

	private static final Logger logger = LoggerFactory.getLogger(MongoDashClient.class);
	
	@Value("#{systemEnvironment['mongodash.db.name'] != null? systemEnvironment['mongodash.db.name'] : properties['mongodash.db.name'] }")
	private String dbname;

	@Value("#{systemEnvironment['mongodash.db.host'] != null? systemEnvironment['mongodash.db.host'] : properties['mongodash.db.host'] }")
	private String host;

	@Value("#{systemEnvironment['mongodash.db.port'] != null? systemEnvironment['mongodash.db.port'] : properties['mongodash.db.port'] }")
	private String port;

	@Value("#{systemEnvironment['mongodash.db.username'] != null? systemEnvironment['mongodash.db.username'] : properties['mongodash.db.username'] }")
	private String username;

	@Value("#{systemEnvironment['mongodash.db.password'] != null? systemEnvironment['mongodash.db.password'] : properties['mongodash.db.password'] }")
	private String password;

	private MongoClient mongoClient;

	@PostConstruct
	private void createClient() throws UnknownHostException {
		logger.debug("Creating MongoClient with connection: Host: {}, Port: {}, Database: {}", new Object[] { host, port, dbname });

		MongoCredential credential = null;
		if (StringUtils.hasText(username) && StringUtils.hasLength(password)) {
			logger.debug("Creating MongoClient with credentials: User: {}, Password: {}", username, password);
			credential = MongoCredential.createMongoCRCredential(username, dbname, password.toCharArray());
			mongoClient = new MongoClient(new ServerAddress(host, Integer.parseInt(port)), Arrays.asList(credential));
		} else {
			logger.debug("Creating MongoClient without credentials: User: {}, Password: {}", username, password);
			mongoClient = new MongoClient(new ServerAddress(host, Integer.parseInt(port)));
		}
	}

	public DB getDashDB() {
		return mongoClient.getDB(dbname);
	}
}