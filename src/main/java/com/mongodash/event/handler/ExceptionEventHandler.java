package com.mongodash.event.handler;

import static com.mongodash.util.ExceptionUtil.isCausedBy;

import javax.annotation.PostConstruct;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.mongodash.event.MongoEventBus;
import com.mongodash.exception.MonitorException;
import com.mongodash.service.NotificationService;
import com.mongodb.MongoException;

@Component
public class ExceptionEventHandler {

	@Autowired
	NotificationService notificationService;
	
	@PostConstruct
	private void subscribeToService() {
		MongoEventBus.register(this);
	}	

	@Subscribe
	public void handleEvent(MonitorException e) {

		//Server server = e.getServer();

		if (isCausedBy(e.getCause(), MongoException.Network.class)) {
		}

		if (isCausedBy(e.getCause(), HttpHostConnectException.class)) {
			//check --rest option
		}
	}
}
