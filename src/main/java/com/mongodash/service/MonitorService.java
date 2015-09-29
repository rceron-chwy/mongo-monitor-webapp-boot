package com.mongodash.service;

import com.mongodash.monitor.MongoMonitor;


public interface MonitorService {
	
	void execute(MongoMonitor monitor);
	
	void remove(String threadName);
	
	boolean isRunning(String threadName);
	
	int getActiveCount();
	
	void shutdown();

}
