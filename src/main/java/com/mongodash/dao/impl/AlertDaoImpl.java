package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.Config.MONGO_COLLECTION;
import com.mongodash.dao.AlertDao;
import com.mongodash.dao.mapper.AlertDBObjectMapper;
import com.mongodash.model.Alert;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Repository
public class AlertDaoImpl extends BaseMongoDao implements AlertDao {

	@Autowired
	private AlertDBObjectMapper objectMapper;

	@Override
	protected String getDomain() {
		return MONGO_COLLECTION.alerts.name();
	}

	@Override
	public List<Alert> list() {
		return find(objectMapper, new BasicDBObject(COMMON_FIELDS.name.name(), 1));
	}

	@Override
	public List<Alert> getActive() {
		return find(objectMapper, new BasicDBObject("active", true), null);
	}

	@Override
	public Alert getById(Integer id) {
		return findOne(new BasicDBObject("_id", id), objectMapper);
	}

	@Override
	public void save(Alert alert) {
		alert.setId(getNextVal());	
		save(alert, objectMapper);

	}

	@Override
	public void update(Alert alert) {
		update(new BasicDBObject(COMMON_FIELDS._id.name(), alert.getId()),
				alert, objectMapper);

	}

	@Override
	public void remove(Alert alert) {
		removeById(alert.getId());

	}

	@Override
	public void removeById(Integer Id) {
		DBCollection dbColl = getCollection();
		dbColl.remove(new BasicDBObject(COMMON_FIELDS._id.name(), Id));
	}

}
