package com.mongodash.model;

import java.util.List;

public class NotificationResponse {

	private int id;
	private List<Notification> notifications;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

}
