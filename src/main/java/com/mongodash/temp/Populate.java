package com.mongodash.temp;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodash.Config;
import com.mongodash.model.Alert;
import com.mongodash.model.Server;
import com.mongodash.model.ServerUpdateMethodType;
import com.mongodash.model.User;
import com.mongodash.service.AlertsService;
import com.mongodash.service.ServerService;
import com.mongodash.service.UserService;
import com.mongodash.util.MongoDashClient;
import com.mongodb.DBCollection;

@Component
public class Populate {

	@Autowired
	ServerService serverService;

	@Autowired
	UserService userService;

	@Autowired
	AlertsService alertService;

	@Autowired
	MongoDashClient mongoClient;

	@PostConstruct
	private void popuplateMongo() {

		DBCollection sequences = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.sequences.name());
		sequences.drop();

		DBCollection notifications = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.notifications.name());
		notifications.drop();

		//populateServers();
		//populateUsers();
		//populateAlerts();
	}

	private void populateAlerts() {
		DBCollection alerts = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.alerts.name());
		alerts.drop();

		Alert a1 = new Alert();
		a1.setId(1);
		a1.setActive(true);
		a1.setField("LOCKED");
		a1.setOperation("GREATER");
		a1.setValue(40);
		alertService.save(a1);

		Alert a2 = new Alert();
		a2.setId(2);
		a2.setActive(false);
		a2.setField("MISSES");
		a2.setOperation("GREATER");
		a2.setValue(60);
		alertService.save(a2);
	}

	private void populateServers() {

		DBCollection servers = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.servers.name());

		servers.drop();

		Server server1 = new Server();
		server1.setName("localhost-tcp");
		server1.setHostname("127.0.0.1");
		server1.setPort(27017);
		server1.setMethod(ServerUpdateMethodType.TCP.name());
		server1.setInterval(2);

		Server server2 = new Server();
		server2.setName("localhost-http");
		server2.setHostname("127.0.0.1");
		server2.setPort(28017);
		server2.setMethod(ServerUpdateMethodType.HTTP.name());
		server2.setInterval(2);

		Server server3 = new Server();
		server3.setName("mongo-106");
		server3.setHostname("10.241.243.106");
		server3.setPort(27017);
		server3.setMethod(ServerUpdateMethodType.HTTP.name());

		Server server4 = new Server();
		server4.setName("localhost2");
		server4.setHostname("127.0.0.1");
		server4.setPort(37017);
		server4.setMethod(ServerUpdateMethodType.AGENT.name());
		server4.setKey(UUID.randomUUID().toString());

		Server server5 = new Server();
		server5.setName("ThiagoLocalhostTest");
		server5.setHostname("127.0.0.1");
		server5.setPort(27012);
		server5.setMethod(ServerUpdateMethodType.TCP.name());
		server5.setKey(UUID.randomUUID().toString());

		Server server6 = new Server();
		server6.setName("CeronServer");
		server6.setHostname("localhost");
		server6.setPort(28017);
		server6.setMethod(ServerUpdateMethodType.HTTP.name());

		Server server7 = new Server();
		server7.setName("CeronServer - Error");
		server7.setHostname("localhost");
		server7.setPort(28019);
		server7.setMethod(ServerUpdateMethodType.HTTP.name());

		serverService.save(server1);
		serverService.save(server2);
		// serverService.save(server3);
		//serverService.save(server4);
		//serverService.save(server5);
		//serverService.save(server6);
		// serverService.save(server7);
		
		Server server10 = new Server();
		server10.setName("DigitalOcean-2.0.9-tcp");
		server10.setHostname("162.243.0.146");
		server10.setPort(30000);
		server10.setMethod(ServerUpdateMethodType.TCP.name());
		server10.setInterval(2);
		
		Server server11 = new Server();
		server11.setName("DigitalOcean-2.0.9-http");
		server11.setHostname("162.243.0.146");
		server11.setPort(31000);
		server11.setMethod(ServerUpdateMethodType.HTTP.name());
		server11.setInterval(2);
		
		Server server12 = new Server();
		server12.setName("DigitalOcean-2.2.7-tcp");
		server12.setHostname("162.243.0.146");
		server12.setPort(30001);
		server12.setMethod(ServerUpdateMethodType.TCP.name());
		server12.setInterval(2);
		
		Server server13 = new Server();
		server13.setName("DigitalOcean-2.2.7-http");
		server13.setHostname("162.243.0.146");
		server13.setPort(31001);
		server13.setMethod(ServerUpdateMethodType.HTTP.name());
		server13.setInterval(2);
		
		Server server14 = new Server();
		server14.setName("DigitalOcean-2.5.4-tcp");
		server14.setHostname("162.243.0.146");
		server14.setPort(30002);
		server14.setMethod(ServerUpdateMethodType.TCP.name());
		server14.setInterval(2);
		
		Server server15 = new Server();
		server15.setName("DigitalOcean-2.5.4-http");
		server15.setHostname("162.243.0.146");
		server15.setPort(31002);
		server15.setMethod(ServerUpdateMethodType.HTTP.name());
		server15.setInterval(2);	
		
		Server server16 = new Server();
		server16.setName("DigitalOcean-2.4.9-tcp");
		server16.setHostname("162.243.0.146");
		server16.setPort(30003);
		server16.setSecure(false);
		server16.setUsername("mongodash");
		server16.setPassword("mongodash");
		server16.setMethod(ServerUpdateMethodType.TCP.name());
		server16.setInterval(2);
		
		Server server17 = new Server();
		server17.setName("DigitalOcean-2.4.9-http");
		server17.setHostname("162.243.0.146");
		server17.setPort(31003);
		server17.setSecure(false);
		server17.setUsername("mongodash");
		server17.setPassword("mongodash");
		server17.setMethod(ServerUpdateMethodType.HTTP.name());
		server17.setInterval(2);			

		serverService.save(server10);
		serverService.save(server11);
		serverService.save(server12);
		serverService.save(server13);
		serverService.save(server14);
		serverService.save(server15);
		serverService.save(server16);
		serverService.save(server17);
		
		
	}

	private void populateUsers() {

		DBCollection users = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.users.name());
		users.drop();

		User u1 = new User();
		u1.setName("Thiago Locatelli");
		u1.setPassword("t");
		u1.setUsername("thiago");
		u1.setEmail("thiago.locatelli@gmail.com");
		u1.setRole("ROLE_ADMIN");
		u1.setActive(true);

		User u2 = new User();
		u2.setName("Rafael Ceron");
		u2.setPassword("!Rafael1");
		u2.setUsername("rafael");
		u2.setEmail("rfceron@gmail.com");
		u2.setRole("ROLE_ADMIN");
		u2.setActive(true);

		User u3 = new User();
		u3.setName("Usuario 3");
		u3.setPassword("!Usuario3");
		u3.setUsername("usuario3");
		u3.setEmail("thiago.locatelli@gmail.com");
		u3.setRole("ROLE_MANAGER");
		u3.setActive(true);

		User u4 = new User();
		u4.setName("Usuario 4");
		u4.setPassword("!Usuario4");
		u4.setUsername("usuario4");
		u4.setEmail("rfceron@gmail.com");
		u4.setRole("ROLE_USER");
		u4.setActive(true);
		
		User u5 = new User();
		u5.setName("Guest User");
		u5.setPassword("!Guest2014!");
		u5.setUsername("guest");
		u5.setEmail("guest@mongodash.com");
		u5.setRole("ROLE_USER");
		u5.setActive(true);

		userService.save(u1);
		userService.save(u2);
		userService.save(u3);
		userService.save(u4);
		userService.save(u5);
	}
}