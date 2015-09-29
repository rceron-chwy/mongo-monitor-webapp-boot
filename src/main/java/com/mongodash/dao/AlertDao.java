package com.mongodash.dao;

import java.util.List;

import com.mongodash.model.Alert;

public interface AlertDao extends BaseDao<Alert> {

	List<Alert> getActive();
	
}
