package com.mongodash.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface DBObjectMapper<T> {

	T mapDBObject(BasicDBObject object);
	
	DBObject mapObject(T object);
	
}
