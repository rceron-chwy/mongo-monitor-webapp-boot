package com.mongodash.event;

import com.mongodash.model.Server;
import com.mongodash.model.ServerStatus;

public class ServerStatusEvent {

	private Server server;
	private ServerStatus serverStatus;

	public ServerStatusEvent(Server server, ServerStatus serverStatus) {
		this.server = server;
		this.serverStatus = serverStatus;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ServerStatus getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(ServerStatus serverStatus) {
		this.serverStatus = serverStatus;
	}

}
