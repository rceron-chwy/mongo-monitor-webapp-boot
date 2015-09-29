package com.mongodash.dao;

import java.util.List;

import com.mongodash.model.Notification;

public interface NotificationDao extends BaseDao<Notification> {

	List<Notification> getNotificationsAfterId(Integer id);
	
	Integer getLastId();
	
	List<Notification> getLastNotifications(Integer total, Integer skip);
	
}
