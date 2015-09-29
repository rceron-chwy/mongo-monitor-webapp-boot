package com.mongodash.service;

import java.util.Date;

import com.mongodash.model.BuildInfo;
import com.mongodash.model.DatabaseInfo;
import com.mongodash.model.HostInfo;
import com.mongodash.model.Response;
import com.mongodash.model.Server;
import com.mongodash.model.Server.Type;

public interface ServerService extends BaseService<Server> {

	Response startMonitor(Integer serverId);
	
	Response stopMonitor(Integer serverId);
	
	void saveHostInfo(HostInfo hostInfo);
	
	void saveBuildInfo(BuildInfo buildInfo);
	
	void saveDatabaseInfo(DatabaseInfo databaseInfo);
	
	HostInfo getHostInfo(Integer serverId);
	
	BuildInfo getBuildInfo(Integer serverId);
	
	DatabaseInfo getDatabaseInfo(Integer serverId);
	
	void removeServerInfo(Integer serverId);
	
	void updateInstanceInfo(Integer serverId, String type, String version, Date uptime);
	
	void updateReplInfo(Integer serverId, Type serverType, String replName);
	
}
