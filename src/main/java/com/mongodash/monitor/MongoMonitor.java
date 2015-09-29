package com.mongodash.monitor;

import static com.mongodash.util.ExceptionUtil.isCausedBy;

import java.net.SocketTimeoutException;
import java.util.Date;

import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodash.event.MongoEventBus;
import com.mongodash.event.ServerEvent;
import com.mongodash.exception.MonitorException;
import com.mongodash.exception.ServerStatusException;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodb.MongoException;

public class MongoMonitor extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(MongoMonitor.class);

	private boolean active = true;
	private Date started;
	private Monitor mongoMonitor;
	private int interval;
	private int currentRetries = 0;

	public MongoMonitor(int interval, Monitor monitor) {
		super(monitor.getServer().getName());
		this.mongoMonitor = monitor;
		this.interval = interval;
	}

	@Override
	public void run() {

		started = new Date();
		logger.info("Monitor started for " + super.getName());

		while (true && active) {
			try {
				logger.debug("Calling monitor.execute() for " + super.getName());
				mongoMonitor.execute(interval);
				sleep(interval, active);
			} catch (MonitorException e) {
				handleException(e);
			}
		}

		logger.info("Monitor finished for " + super.getName());

	}

	private void handleException(MonitorException e) {

		e.printStackTrace();
		
		if(isCausedBy(e.getCause(), NullPointerException.class)) {
			active = false;
			MongoEventBus.post(e,
				new Notification(NotificationType.SERVER_DOWN, mongoMonitor.getServer().getName()),
				new ServerEvent(NotificationType.SERVER_DOWN, mongoMonitor.getServer()));
		}
		else if(isCausedBy(e.getCause(), InvalidCredentialsException.class)) {
			active = false;
			logger.error("Invalid Credentials for server " + e.getServer().getName());
			MongoEventBus.post(new Notification(NotificationType.INVALID_CREDENTAIS, 
					mongoMonitor.getServer().getName()));			
		}
		else if(isCausedBy(e.getCause(), SocketTimeoutException.class)) {
			active = false;
			MongoEventBus.post(e,
				new Notification(NotificationType.SERVER_DOWN, mongoMonitor.getServer().getName()),
				new ServerEvent(NotificationType.SERVER_DOWN, mongoMonitor.getServer()));
		}
		else if (isCausedBy(e.getCause(), MongoException.Network.class) || 
				isCausedBy(e.getCause(), HttpHostConnectException.class) ||
				isCausedBy(e.getCause(), ServerStatusException.class)  ||
				isCausedBy(e.getCause(), RuntimeException.class)) {

			logger.error(e.getCause().getMessage());
			logger.error("Cause: " + e.getCause());
			if (currentRetries == 1) {
				logger.error("Limit of retries reached, disabling monitor");
				active = false;
				MongoEventBus.post(e,
						new Notification(NotificationType.SERVER_DOWN, mongoMonitor.getServer().getName()),
						new ServerEvent(NotificationType.SERVER_DOWN, mongoMonitor.getServer()));
			}
			currentRetries++;
		}
	}

	private void sleep(int interval, boolean isActive) {
		for (int i = 0; i < (interval * 2) && isActive; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.debug("Sleep interval interrupted for " + super.getName());
			}
		}
	}

	public void setActive(boolean _active) {
		synchronized (this) {
			active = _active;
		}
	}

	public Date getStarted() {
		return started;
	}
}
