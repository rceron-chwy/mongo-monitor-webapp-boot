package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.dao.NotificationDao;
import com.mongodash.dao.mapper.NotificationDBObjectMapper;
import com.mongodash.model.Notification;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class NotificationDaoImpl extends BaseMongoDao implements NotificationDao {

	@Autowired
	private NotificationDBObjectMapper objectMapper;
	
	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.notifications.name();
	}


	@Override
	public List<Notification> list() {
		throw new IllegalArgumentException("Method not implemented");
	}

	@Override
	public Notification getById(Integer id) {
		throw new IllegalArgumentException("Method not implemented");	
	}

	@Override
	public void save(Notification notification) {
		notification.setId(getNextVal());
		save(notification, objectMapper);
		
	}

	@Override
	public void update(Notification t) {
		throw new IllegalArgumentException("Notifications cannot be updated");	
	}

	@Override
	public void remove(Notification t) {
		throw new IllegalArgumentException("Notifications cannot be removed");	
	}

	@Override
	public void removeById(Integer Id) {
		throw new IllegalArgumentException("Notifications cannot be removed");
	}

	@Override
	public List<Notification> getNotificationsAfterId(Integer id) {
		DBObject query = new BasicDBObject();
		query.put("_id", new BasicDBObject("$gt", id));	
		return find(objectMapper, query, null);
	}

	@Override
	public Integer getLastId() {
		DBCollection dbColl = getCollection();
		DBObject sort = new BasicDBObject("_id", -1);
		DBCursor cursor = dbColl.find().sort(sort).limit(1);
		if(cursor.hasNext()) {
			DBObject obj = cursor.next();
			return (Integer) obj.get("_id");
		}
		return -1;
	}


	@Override
	public List<Notification> getLastNotifications(Integer total, Integer skip) {
		DBObject sort = new BasicDBObject("_id", -1);
		return find(objectMapper, null, sort, total, skip);
	}

}
