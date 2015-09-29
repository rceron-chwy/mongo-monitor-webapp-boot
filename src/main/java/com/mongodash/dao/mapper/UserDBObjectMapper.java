package com.mongodash.dao.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mongodash.Config;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class UserDBObjectMapper implements DBObjectMapper<User> {

	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User mapDBObject(BasicDBObject object) {
		User user = new User();
		user.setId(object.getInt(Config.COMMON_FIELDS._id.name()));
		user.setName(object.getString(Config.COMMON_FIELDS.name.name()));
		user.setUsername(object.getString(User.fields.username.name()));
		user.setPassword(object.getString(User.fields.password.name())); //never decrypt
		user.setCurrentPassword(object.getString(User.fields.password.name()));
		user.setEmail(object.getString(User.fields.email.name()));
		user.setActive(object.getBoolean(Config.COMMON_FIELDS.active.name()));
		user.setRole(object.getString(User.fields.role.name()));
		return user;
	}

	@Override
	public DBObject mapObject(User object) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Config.COMMON_FIELDS._id.name(), object.getId());
		dbObject.put(Config.COMMON_FIELDS.name.name(), object.getName());
		dbObject.put(User.fields.email.name(), object.getEmail());
		dbObject.put(User.fields.role.name(), object.getRole());
		dbObject.put(User.fields.username.name(), object.getUsername());
		dbObject.put(Config.COMMON_FIELDS.active.name(), object.isActive());
		dbObject.put(Config.COMMON_FIELDS.updated.name(), new Date());
		
		//update password only if it changed
		String password = object.getPassword();
		if (password != null && !password.equals(object.getCurrentPassword())) {
			dbObject.put(User.fields.password.name(), passwordEncoder.encode(password));
		}
		
		return dbObject;
	}
}