package com.mongodash.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodash.Config;
import com.mongodash.dao.UserDao;
import com.mongodash.dao.mapper.UserDBObjectMapper;
import com.mongodash.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Repository
public class UserDaoImpl extends BaseMongoDao implements UserDao {

	@Autowired
	private UserDBObjectMapper objectMapper;

	@Override
	protected String getDomain() {
		return Config.MONGO_COLLECTION.users.name();
	}

	@Override
	public List<User> list() {
		return find(objectMapper, new BasicDBObject(Config.COMMON_FIELDS.name.name(), 1));
	}

	@Override
	public User getById(Integer id) {
		return findOne(new BasicDBObject("_id", id), objectMapper);
	}

	@Override
	public void save(User user) {
		user.setId(getNextVal());
		save(user, objectMapper);
	}

	@Override
	public void update(User user) {
		User currentUser = getById(user.getId());
		user.setCurrentPassword(currentUser.getPassword());

		update(new BasicDBObject(Config.COMMON_FIELDS._id.name(), user.getId()), user, objectMapper);

	}

	@Override
	public void remove(User user) {
		removeById(user.getId());
	}

	@Override
	public void removeById(Integer Id) {
		DBCollection dbColl = getCollection();
		dbColl.remove(new BasicDBObject(Config.COMMON_FIELDS._id.name(), Id));
	}

	@Override
	public User findUserByUsername(String username) {
		return findOne(new BasicDBObject(User.fields.username.name(), username), objectMapper);
	}
}
