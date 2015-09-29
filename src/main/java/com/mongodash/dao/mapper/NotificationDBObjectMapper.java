package com.mongodash.dao.mapper;

import org.springframework.stereotype.Component;

import com.mongodash.Config;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class NotificationDBObjectMapper implements DBObjectMapper<Notification> {

	@Override
	public Notification mapDBObject(BasicDBObject object) {
		Notification notification = new Notification(NotificationType.valueOf(object.getString("type")));
		notification.setText(object.getString("text"));
		notification.setId(object.getInt(Config.COMMON_FIELDS._id.name()));
		return notification;
	}

	@Override
	public DBObject mapObject(Notification object) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Config.COMMON_FIELDS._id.name(), object.getId());
		dbObject.put("type", object.getType().name());
		dbObject.put("text", object.getText());
		return dbObject;
	}
}
