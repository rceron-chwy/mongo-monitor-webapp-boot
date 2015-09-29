package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.dao.ReportDao;
import com.mongodash.dao.mapper.ReportDBObjectMapper;
import com.mongodash.model.Report;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Repository
public class ReportDaoImpl extends BaseMongoDao implements ReportDao {
	
	@Autowired
	private ReportDBObjectMapper objectMapper;
	
	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.reports.name();
	}

	
	@Override
	public List<Report> list() {
		return find(objectMapper);
	}

	@Override
	public Report getById(Integer id) {
		return findOne(new BasicDBObject("_id", id), objectMapper);
	}

	@Override
	public void save(Report t) {
		t.setId(getNextVal());	
		save(t, objectMapper);

	}

	@Override
	public void update(Report t) {
		update(new BasicDBObject(Config.COMMON_FIELDS._id.name(), t.getId()), 
				t, objectMapper);

	}

	@Override
	public void remove(Report t) {
		removeById(t.getId());
	}

	@Override
	public void removeById(Integer Id) {
		DBCollection dbColl = getCollection();
		dbColl.remove(new BasicDBObject(Config.COMMON_FIELDS._id.name(), Id));
	}

	@Override
	public AggregationOutput runReport(DBObject projection, DBObject match, DBObject group, DBObject sort) {
		DB db = getCollection().getDB();
		
		return db.getCollection(
				Config.MONGO_COLLECTION.serverStats.name()).aggregate(projection, match, group, sort);
	}

}
