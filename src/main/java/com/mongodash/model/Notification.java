package com.mongodash.model;

public class Notification {

	private Integer id;
	private String text;
	private NotificationType type;
	private String object;
	private String subject;

	public Notification(NotificationType type) {
		this.type = type;
	}
	
	public Notification(NotificationType type, String object) {
		this.type = type;
		this.object = object;
	}

	public Notification(NotificationType type, String object, String subject) {
		this.type = type;
		this.object = object;
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
