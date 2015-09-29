package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.Config.COMMON_FIELDS;
import com.mongodash.dao.BackupDao;
import com.mongodash.dao.mapper.BackupDBObjectMapper;
import com.mongodash.model.Backup;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Repository
public class BackupDaoImpl extends BaseMongoDao implements BackupDao {

	@Autowired
	private BackupDBObjectMapper objectMapper;
	
	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.backup.name();
	}

	@Override
	public List<Backup> list() {
		return find(objectMapper, new BasicDBObject(COMMON_FIELDS.name.name(), 1));
	}

	@Override
	public Backup getById(Integer id) {
		return findOne(new BasicDBObject("_id", id), objectMapper);
	}

	@Override
	public void save(Backup t) {
		t.setId(getNextVal());	
		save(t, objectMapper);
	}

	@Override
	public void update(Backup t) {
		update(new BasicDBObject(Config.COMMON_FIELDS._id.name(), t.getId()), t, objectMapper);
	}

	@Override
	public void remove(Backup t) {
		removeById(t.getId());
	}

	@Override
	public void removeById(Integer Id) {
		DBCollection dbColl = getCollection();
		dbColl.remove(new BasicDBObject(COMMON_FIELDS._id.name(), Id));
	}

}
