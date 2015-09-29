package com.mongodash.service;

import java.util.List;

import com.mongodash.model.ServerStatus;


public interface StatsService {

	void saveServerStats(ServerStatus serverStatus);
	
	List<ServerStatus> getLastServerStatus(Integer serverId);
	
}
