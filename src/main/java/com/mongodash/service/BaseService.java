package com.mongodash.service;

import java.util.List;

public interface BaseService<T> {

	List<T> list();
	
	T getById(Integer id);
	
	void save(T t);
	
	void update(T t);
	
	void remove(T t);
	
	void removeById(Integer id);
	
}
