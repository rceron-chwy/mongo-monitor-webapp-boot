package com.mongodash.service;

import java.util.List;

import com.mongodash.model.Alert;

public interface AlertsService extends BaseService<Alert>{

	List<Alert> listActive();
	
}
