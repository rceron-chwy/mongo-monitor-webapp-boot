package com.mongodash.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.dao.SettingsDao;
import com.mongodash.dao.mapper.SettingsDBObjectMapper;
import com.mongodash.model.Settings;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Repository
public class SettingsDaoImpl extends BaseMongoDao implements SettingsDao {
	
	@Autowired
	private SettingsDBObjectMapper objectMapper;
	
	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.settings.name();	
	}


	@Override
	public Settings getSettings(SETTINGS_KEY settingsKey) {
		DBObject query = new BasicDBObject("_id", settingsKey.name().toString());
		return findOne(query, objectMapper);
	}
	
	@Override
	public void saveSettings(Settings settings) {
		DBObject query = new BasicDBObject("_id", settings.getKey().name());
		update(query, settings, objectMapper, true, false);
	}

	@Override
	public boolean isConfigActive(SETTINGS_KEY settingsKey) {
		DBCollection coll = getCollection();
		DBObject query = new BasicDBObject("_id", settingsKey.name().toString());
		query.put("enabled", true);
		if(coll.findOne(query) != null) {
			return true;
		}
		return false;
	}

}
