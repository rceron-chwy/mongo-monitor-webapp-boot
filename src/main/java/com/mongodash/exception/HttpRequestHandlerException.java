package com.mongodash.exception;


public class HttpRequestHandlerException extends Exception {

	private static final long serialVersionUID = 5169376710413754318L;
	
	public HttpRequestHandlerException(String msg) {
		super(msg);
	}
	
	public HttpRequestHandlerException(String msg, Throwable t) {
		super(msg, t);
	}
}
