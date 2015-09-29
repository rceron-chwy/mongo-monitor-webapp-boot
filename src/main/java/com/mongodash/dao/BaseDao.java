package com.mongodash.dao;

import java.util.List;

public interface BaseDao<T> {

	List<T> list();
	
	T getById(Integer id);
	
	void save(T t);
	
	void update(T t);
	
	void remove(T t);
	
	void removeById(Integer Id);
	
}
