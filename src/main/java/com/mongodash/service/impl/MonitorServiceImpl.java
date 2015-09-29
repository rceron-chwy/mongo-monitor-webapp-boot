package com.mongodash.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.mongodash.monitor.MongoMonitor;
import com.mongodash.service.MonitorService;

@Component
public class MonitorServiceImpl implements MonitorService {

	private Map<String, MongoMonitor> currentThreads = new HashMap<String, MongoMonitor>();

	private ThreadPoolExecutor executorPool = null;

	@PostConstruct
	private void setup() {
		executorPool = new ThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), Executors.defaultThreadFactory());
	}

	public void execute(MongoMonitor monitor) {
		if (!isRunning(monitor.getName())) {
			currentThreads.put(monitor.getName(), monitor);
			executorPool.execute(monitor);
		}
	}

	public void remove(String threadName) {
		MongoMonitor t = currentThreads.remove(threadName);
		if (t != null) {
			t.setActive(false);
		}
	}

	public boolean isRunning(String threadName) {
		return currentThreads.containsKey(threadName);
	}

	public int getActiveCount() {
		return currentThreads.size();
	}

	public void shutdown() {
		if (executorPool.getActiveCount() > 0) {
			executorPool.shutdownNow();
		} else {
			executorPool.shutdown();
		}
	}
}
