package com.mongodash.model;

public enum ServerUpdateMethodType {

	TCP("Local Thread - TCP"), 
	HTTP("Local Thread - HTTP"), 
	AGENT("Remote Agent");

	private String text;

	private ServerUpdateMethodType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
