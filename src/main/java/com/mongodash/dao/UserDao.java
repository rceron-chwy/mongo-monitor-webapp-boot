package com.mongodash.dao;

import com.mongodash.model.User;

public interface UserDao extends BaseDao<User> {
	
	User findUserByUsername(String username);

}
