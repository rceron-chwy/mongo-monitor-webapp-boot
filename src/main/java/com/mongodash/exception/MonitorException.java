package com.mongodash.exception;

import com.mongodash.model.Server;

public class MonitorException extends Exception {

	private static final long serialVersionUID = 6449688781045693763L;

	private Server server;
	
	public MonitorException(String msg, Server server , Throwable t) {
		super(msg, t);
		this.server = server;
	}
	
	public MonitorException(Server server, Throwable t) {
		super(t);
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

}
