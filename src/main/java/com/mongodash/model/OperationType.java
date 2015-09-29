package com.mongodash.model;

public enum OperationType {

	EQUAL("Equal to"),
	GREATER("Greater than"),
	LESS("Less than");
	
	private String text;
	
	private OperationType(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
