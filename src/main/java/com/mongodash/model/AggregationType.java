package com.mongodash.model;

public enum AggregationType {

	AVG("Average of all the values"),
	MIN("Lowest value in a group"),
	MAX("Highest value in a group"),
	FIRST("First value in a group"),
	LAST("Last value in a group"),
	SUM("Sum of all the values");
	
	private String text;
	
	private AggregationType(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
