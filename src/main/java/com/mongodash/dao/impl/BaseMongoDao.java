package com.mongodash.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodash.Config;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.util.MongoDashClient;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public abstract class BaseMongoDao {

	@Autowired
	MongoDashClient mongoClient;

	protected int getNextVal() {
		DBCollection db = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.sequences.name());
		DBObject query = new BasicDBObject("_id", getDomain());
		DBObject update = new BasicDBObject("$inc", new BasicDBObject("seq", 1));
		DBObject result = db.findAndModify(query, null, null, false, update, true, true);
		return (Integer) result.get("seq");
	}

	protected int getCurrentVal() {
		DBCollection db = mongoClient.getDashDB().getCollection(Config.MONGO_COLLECTION.sequences.name());
		DBObject query = new BasicDBObject("_id", getDomain());
		DBObject result = db.findOne(query);
		return (Integer) result.get("seq");
	}

	protected DBCollection getCollection() {
		return mongoClient.getDashDB().getCollection(getDomain());
	}

	protected <T> List<T> find(DBObjectMapper<T> objectMapper) {
		return find(objectMapper, null, null);
	}

	protected <T> List<T> find(DBObjectMapper<T> objectMapper, DBObject sort) {
		return find(objectMapper, null, sort);
	}

	protected <T> List<T> find(DBObjectMapper<T> objectMapper, DBObject query, DBObject sort) {
		List<T> result = null;
		DBCollection dbCollection = getCollection();

		DBCursor dbCursor = query == null ? ((sort == null) ? dbCollection.find() : dbCollection.find().sort(sort)) : ((sort == null) ? dbCollection
				.find(query) : dbCollection.find(query).sort(sort));

		if (dbCursor.hasNext()) {
			result = new ArrayList<T>();
			while (dbCursor.hasNext()) {
				T obj = objectMapper.mapDBObject((BasicDBObject) dbCursor.next());
				result.add(obj);
			}
		}
		dbCursor.close();
		return result;
	}

	protected <T> List<T> find(DBObjectMapper<T> objectMapper, DBObject query, DBObject sort, int limit) {
		List<T> result = null;
		DBCollection dbCollection = getCollection();

		DBCursor dbCursor = (query == null) ? ((sort == null) ? dbCollection.find().limit(limit) : dbCollection.find().sort(sort)).limit(limit)
				: ((sort == null) ? dbCollection.find(query).limit(limit) : dbCollection.find(query).sort(sort)).limit(limit);

		if (dbCursor.hasNext()) {
			result = new ArrayList<T>();
			while (dbCursor.hasNext()) {
				T obj = objectMapper.mapDBObject((BasicDBObject) dbCursor.next());
				result.add(obj);
			}
		}
		dbCursor.close();
		return result;
	}
	
	protected <T> List<T> find(DBObjectMapper<T> objectMapper, DBObject query, DBObject sort, int limit, int skip) {
		List<T> result = null;
		DBCollection dbCollection = getCollection();

		DBCursor dbCursor = (query == null) ? ((sort == null) ? dbCollection.find().limit(limit).skip(skip) : dbCollection.find().sort(sort)).limit(limit).skip(skip)
				: ((sort == null) ? dbCollection.find(query).limit(limit).skip(skip) : dbCollection.find(query).sort(sort)).limit(limit).skip(skip);

		if (dbCursor.hasNext()) {
			result = new ArrayList<T>();
			while (dbCursor.hasNext()) {
				T obj = objectMapper.mapDBObject((BasicDBObject) dbCursor.next());
				result.add(obj);
			}
		}
		dbCursor.close();
		return result;
	}

	protected <T> T findOne(DBObject query, DBObjectMapper<T> objectMapper) {
		T result = null;
		DBCollection dbCollection = getCollection();
		DBObject object = dbCollection.findOne(query);
		if (object != null) {
			result = objectMapper.mapDBObject((BasicDBObject) object);
		}
		return result;
	}

	protected <T> void save(T object, DBObjectMapper<T> objectMapper) {
		DBCollection dbCollection = getCollection();
		dbCollection.insert(objectMapper.mapObject(object));
	}
	
	protected <T> void update(DBObject query, T object, DBObjectMapper<T> objectMapper) {
		DBCollection dbCollection = getCollection();
		dbCollection.update(query, objectMapper.mapObject(object));
	}

	protected <T> void update(DBObject query, T object, DBObjectMapper<T> objectMapper, boolean upsert, boolean multi) {
		DBCollection dbCollection = getCollection();
		dbCollection.update(query, objectMapper.mapObject(object), upsert, multi);
	}

	abstract protected String getDomain();
}
