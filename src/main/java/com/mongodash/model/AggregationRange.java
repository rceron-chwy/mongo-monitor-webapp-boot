package com.mongodash.model;

public enum AggregationRange {

	MONTH("Last 30 days"),
	WEEK("Last 7 days"),
	LASTDAY("Last day"),
	TODAY("Current day");
	
	private String text;
	
	private AggregationRange(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
