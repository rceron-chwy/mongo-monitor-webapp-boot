package com.mongodash.exception;

public class ServerStatusException extends Exception {

	private static final long serialVersionUID = -8622656962492727948L;

	public ServerStatusException(String msg) {
		super(msg);
	}

	public ServerStatusException(String msg, Throwable t) {
		super(msg, t);
	}
}
