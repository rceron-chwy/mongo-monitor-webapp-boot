package com.mongodash.event;

import com.mongodash.model.NotificationType;
import com.mongodash.model.Server;

public class ServerEvent {

	private NotificationType eventType;
	private Server server;

	public ServerEvent(NotificationType eventType, Server server) {
		this.eventType = eventType;
		this.server = server;
	}

	public NotificationType getEventType() {
		return eventType;
	}

	public void setEventType(NotificationType eventType) {
		this.eventType = eventType;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}
