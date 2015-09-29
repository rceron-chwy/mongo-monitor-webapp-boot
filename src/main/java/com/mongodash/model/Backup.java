package com.mongodash.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Backup extends BaseModel {

	public static enum fields {
		serverId, compressFolder, runDaily, includeOpLog, notifyOnFinish, databases, outputFolder, lastExecution, dumpFormat
	}

	@NotEmpty
	@NotNull
	private String name;
	
	@NotNull
	private Integer serverId;
	
	private boolean compressFolder;
	private boolean runDaily;
	private boolean includeOpLog;
	private boolean notifyOnFinish;
	
	@NotEmpty
	@NotNull
	private String dumpFormat;
	
	private List<String> databases;
	
	@NotEmpty
	@NotNull
	private String outputFolder;
	
	private Date lastExecution;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public boolean isCompressFolder() {
		return compressFolder;
	}

	public void setCompressFolder(boolean compressFolder) {
		this.compressFolder = compressFolder;
	}

	public boolean isRunDaily() {
		return runDaily;
	}

	public void setRunDaily(boolean runDaily) {
		this.runDaily = runDaily;
	}

	public List<String> getDatabases() {
		return databases;
	}

	public void setDatabases(List<String> databases) {
		this.databases = databases;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public boolean isNotifyOnFinish() {
		return notifyOnFinish;
	}

	public void setNotifyOnFinish(boolean notifyOnFinish) {
		this.notifyOnFinish = notifyOnFinish;
	}

	public boolean isIncludeOpLog() {
		return includeOpLog;
	}

	public void setIncludeOpLog(boolean includeOpLog) {
		this.includeOpLog = includeOpLog;
	}

	public Date getLastExecution() {
		return lastExecution;
	}

	public void setLastExecution(Date lastExecution) {
		this.lastExecution = lastExecution;
	}

	public String getDumpFormat() {
		return dumpFormat;
	}

	public void setDumpFormat(String dumpFormat) {
		this.dumpFormat = dumpFormat;
	}

}
