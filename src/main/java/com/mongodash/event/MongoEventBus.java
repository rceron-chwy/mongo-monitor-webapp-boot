package com.mongodash.event;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.AsyncEventBus;

public class MongoEventBus {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoEventBus.class);

	//private static EventBus eventBus = new EventBus();
	private static AsyncEventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
	
	public static void register(Object object) {
		logger.debug("Registering {} to the MongoEventBus", object);
		eventBus.register(object);
	}
	
	public static void post(Object... events) {
		for(Object event : events) {
			logger.debug("Posting {} to the MongoEventBus", event);
			eventBus.post(event);
		}
	}
}
