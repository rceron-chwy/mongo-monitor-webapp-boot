package com.mongodash.dao.mapper;

import org.springframework.stereotype.Component;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.Alert;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class AlertDBObjectMapper implements DBObjectMapper<Alert> {

	@Override
	public Alert mapDBObject(BasicDBObject object) {
		Alert alert = new Alert();
		alert.setId(object.getInt(COMMON_FIELDS._id.name()));
		alert.setActive(object.getBoolean(COMMON_FIELDS.active.name()));
		alert.setField(object.getString(Alert.fields.field.name()));
		alert.setOperation(object.getString(Alert.fields.operation.name()));
		alert.setValue(object.getInt(Alert.fields.value.name()));
		alert.setRecipientList(object.getString(Alert.fields.recipientList.name()));
		return alert;
	}

	@Override
	public DBObject mapObject(Alert object) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(COMMON_FIELDS._id.name(), object.getId());
		dbObject.put(COMMON_FIELDS.active.name(), object.isActive());
		dbObject.put(Alert.fields.field.name(), object.getField());
		dbObject.put(Alert.fields.operation.name(), object.getOperation());
		dbObject.put(Alert.fields.value.name(), object.getValue());
		dbObject.put(Alert.fields.recipientList.name(), object.getRecipientList());
		return dbObject;
	}
}
