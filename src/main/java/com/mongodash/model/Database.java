package com.mongodash.model;

public class Database {

	private String name;
	private Long sizeOnDisk;
	private boolean empty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSizeOnDisk() {
		return sizeOnDisk;
	}

	public void setSizeOnDisk(Long sizeOnDisk) {
		this.sizeOnDisk = sizeOnDisk;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	@Override
	public String toString() {
		return "Database [name=" + name + ", sizeOnDisk=" + sizeOnDisk + ", empty=" + empty + "]";
	}

}
