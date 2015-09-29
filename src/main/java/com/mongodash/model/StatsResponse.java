package com.mongodash.model;

import java.util.List;

public class StatsResponse {

	private List<ServerStatus> stats;

	public List<ServerStatus> getStats() {
		return stats;
	}

	public void setStats(List<ServerStatus> stats) {
		this.stats = stats;
	}
	
}
