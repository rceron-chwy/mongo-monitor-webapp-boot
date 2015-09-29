package com.mongodash.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mongodash.model.User;

public interface UserService extends BaseService<User>, UserDetailsService {

	User findUserByUsername(String username);
	
}
