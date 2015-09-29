package com.mongodash.model;

public enum AlertField {

	QUERY("Queries", "query"),
	INSERT("Inserts", "insert"),
	DELETE("Deletes", "delete"),
	UPDATE("Updates", "update"),
	COMMAND("Commands", "command"),
	GETMORE("Get more", "getmore"),
	FLUSHES("Flushes", "flushes"),
	VSIZE("Virtual Memory (MB)", "virtual"),
	RES("Resident Memory (MB)", "res"),
	MAPPED("Mapped Memory (MB)", "mapped"),
	NONMAPPED("Non-mapped memory (MB)", "nonmapped"),
	FAULTS("Faults","faults"),
	LOCKED("Database locks (%)", "locked"),
	MISSES("Index misses (%)","misses"),
	QR("Queued readers","qr"),
	QW("Queued writers","qw"),
	AR("Active readers","ar"),
	AW("Active writers","aw"),
	CONN("Current connections","conn"),
	AVAL("Available connections","aval");
	
	private String text;
	private String field;
	
	private AlertField(String text, String field) {
		this.text = text;
		this.field = field;
	}
	
	public String getText() {
		return text;
	}
	
	public String getField() {
		return field;
	}
	
}
