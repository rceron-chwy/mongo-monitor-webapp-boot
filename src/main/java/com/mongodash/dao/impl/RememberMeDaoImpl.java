package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.dao.RememberMeDao;
import com.mongodash.dao.mapper.RememberMeDBObjectMapper;
import com.mongodash.model.RememberMeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Repository
public class RememberMeDaoImpl extends BaseMongoDao implements RememberMeDao {

	@Autowired
	private RememberMeDBObjectMapper objectMapper;

	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.rememberMe.name();
	}

	@Override
	public List<RememberMeToken> list() {
		throw new IllegalArgumentException("Method not implemented");
	}

	@Override
	public RememberMeToken getById(Integer id) {
		throw new IllegalArgumentException("Method not implemented");
	}

	@Override
	public void save(RememberMeToken token) {
		save(token, objectMapper);
	}

	@Override
	public void update(RememberMeToken token) {
		update(new BasicDBObject(RememberMeToken.fields.series.name(), token.getSeries()), token, objectMapper);
	}
	

	@Override
	public void remove(RememberMeToken token) {
		throw new IllegalArgumentException("Method not implemented");
	}
	
	public void removeByUserName(String username) {
		DBCollection dbColl = getCollection();
		dbColl.remove(new BasicDBObject(RememberMeToken.fields.username.name(), username));
	}

	@Override
	public void removeById(Integer Id) {
		throw new IllegalArgumentException("Method not implemented");
	}

	@Override
	public RememberMeToken findBySeries(String series) {
		return findOne(new BasicDBObject(RememberMeToken.fields.series.name(), series), objectMapper);
	}
}
