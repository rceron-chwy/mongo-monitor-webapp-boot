package com.mongodash.dao.mapper;

import org.springframework.stereotype.Component;

import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.RememberMeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class RememberMeDBObjectMapper implements DBObjectMapper<RememberMeToken> {


	@Override
	public RememberMeToken mapDBObject(BasicDBObject object) {
		RememberMeToken token = new RememberMeToken();
		token.setDate(object.getDate(RememberMeToken.fields.date.name()));
		token.setUsername(object.getString(RememberMeToken.fields.username.name()));
		token.setTokenValue(object.getString(RememberMeToken.fields.tokenValue.name()));
		token.setSeries(object.getString(RememberMeToken.fields.series.name()));
		return token;
	}

	@Override
	public DBObject mapObject(RememberMeToken object) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(RememberMeToken.fields.username.name(), object.getUsername());
		dbObject.put(RememberMeToken.fields.tokenValue.name(), object.getTokenValue());
		dbObject.put(RememberMeToken.fields.series.name(), object.getSeries());
		dbObject.put(RememberMeToken.fields.date.name(), object.getDate());
		return dbObject;
	}
}