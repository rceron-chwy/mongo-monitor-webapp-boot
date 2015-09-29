package com.mongodash.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodash.model.Server;
import com.mongodash.model.ServerUpdateMethodType;
import com.mongodash.service.ServerService;

@Component
public class AgentKeysHolder {

	private Map<String, Server> agentsKey = new HashMap<String, Server>();

	@Autowired
	ServerService serverService;	
	
	@PostConstruct
	public void loadKeys() {
		List<Server> servers = serverService.list();
		agentsKey.clear();
		if(servers != null) {
			for(Server server : servers) {
				if(ServerUpdateMethodType.AGENT.name().equals(server.getMethod())) {
					addKey(server.getKey(), server);
				}
			}
		}
	}
	
	public boolean isValidKey(String agentKey) {
		for(String key : agentsKey.keySet()) {
			if(key != null && key.equals(agentKey)) return true;
		}
		return false;
	}
	
	public void removeKey(String agentKey) {
		for(String key : agentsKey.keySet()) {
			if(agentsKey.equals(key)) agentsKey.remove(key);
			return;
		}		
	}
	
	public Server getServer(String agentKey) {
		return agentsKey.get(agentKey);
	}
	
	public void addKey(String agentKey, Server server) {
		if(!isValidKey(agentKey)) {
			agentsKey.put(agentKey, server);
		}
	}
}
