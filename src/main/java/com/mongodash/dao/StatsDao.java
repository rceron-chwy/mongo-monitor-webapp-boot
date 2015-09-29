package com.mongodash.dao;

import java.util.List;

import com.mongodash.model.ServerStatus;

public interface StatsDao {

	void saveServerStats(ServerStatus serverStatus);
	
	List<ServerStatus> getLastServerStatus(Integer serverId, int limit);
	
}
