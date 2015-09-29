package com.mongodash.monitor;

import com.mongodash.exception.MonitorException;
import com.mongodash.model.Server;

public interface Monitor {

	void execute(int seconds) throws MonitorException;
	
	void setServer(Server server);
	
	Server getServer();
	
}
