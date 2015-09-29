package com.mongodash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mongodash.dao.UserDao;
import com.mongodash.event.MongoEventBus;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.model.User;
import com.mongodash.model.UserRole;
import com.mongodash.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public List<User> list() {
		return userDao.list();
	}

	@Override
	public User getById(Integer id) {
		return userDao.getById(id);
	}

	@Override
	public void save(User user) {
		userDao.save(user);
		MongoEventBus.post(new Notification(NotificationType.USER_CREATED, user.getUsername()));
	}

	@Override
	public void update(User user) {
		userDao.update(user);
		MongoEventBus.post(new Notification(NotificationType.USER_UPDATED, user.getUsername()));
	}

	@Override
	public void remove(User user) {
		userDao.remove(user);
		MongoEventBus.post(new Notification(NotificationType.USER_REMOVED, user.getUsername()));
	}

	@Override
	public void removeById(Integer id) {
		User user = userDao.getById(id);
		if (user != null) {
			remove(user);
		}
		
		if(list() == null || list().size() == 0) {
			user = new User();
			user.setActive(true);
			user.setName("Administrator");
			user.setRole(UserRole.ROLE_ADMIN.name());
			user.setUsername("admin");
			user.setPassword("!admin");
			save(user);
		}
		
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserDetails user = findUserByUsername(username);
		 if (user == null) throw new UsernameNotFoundException(username);
		 return user;
	}
}