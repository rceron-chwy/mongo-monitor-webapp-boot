package com.mongodash.model;

import java.util.List;

public class DatabaseInfo {

	private Integer id;
	private Long totalSize;
	private List<Database> database;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public List<Database> getDatabase() {
		return database;
	}

	public void setDatabase(List<Database> database) {
		this.database = database;
	}

	@Override
	public String toString() {
		return "Databases [id=" + id + ", totalSize=" + totalSize + ", database=" + database + "]";
	}

}
