package com.mongodash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodash.dao.StatsDao;
import com.mongodash.model.ServerStatus;
import com.mongodash.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

	@Autowired
	private StatsDao statsDao;

	@Override
	public void saveServerStats(ServerStatus serverStatus) {
		statsDao.saveServerStats(serverStatus);
	}

	@Override
	public List<ServerStatus> getLastServerStatus(Integer serverId) {
		return statsDao.getLastServerStatus(serverId, 10);
	}
}
