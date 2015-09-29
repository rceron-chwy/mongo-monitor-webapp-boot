package com.mongodash.service;

import java.util.List;

import com.mongodash.model.Notification;

public interface NotificationService extends BaseService<Notification> {

	Integer getLastId();
	
	List<Notification> getNotificationsAfterId(Integer id);
	
	List<Notification> getNotificationsByPage(Integer page);
	
	
}
