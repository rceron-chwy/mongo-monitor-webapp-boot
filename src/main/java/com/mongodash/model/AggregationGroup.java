package com.mongodash.model;

public enum AggregationGroup {

	MONTH("Group results by Month"),
	DAY("Group results by Day"),
	HOUR("Group results by Hour"),
	MINUTE("Group results by Minute");
	
	private String text;
	
	private AggregationGroup(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
