package com.mongodash.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class Server extends BaseModel {

	public static enum fields {
		hostname, port, username, password, method, sleep, uptime, key, secure, retain, lastMonitorUpdate, process, type, version, replName;
	}

	public static enum Type {
		standalone, master, secondary
	}

	@NotNull
	@Size(min = 3)
	private String name;

	@NotNull
	private String hostname;

	@NumberFormat(style = Style.NUMBER)
	@NotNull
	private Integer port;

	private String username;
	private String password;
	private String method;

	@NotNull
	private Integer interval;

	@NotNull
	private Integer retain;

	private String key;

	private boolean secure;
	private boolean monitorRunning;
	private Date lastMonitorUpdate;

	private String process;
	private Date upTime;
	private String type;
	private String version;
	private String replName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isMonitorRunning() {
		return monitorRunning;
	}

	public void setMonitorRunning(boolean monitorRunning) {
		this.monitorRunning = monitorRunning;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public Integer getRetain() {
		return retain;
	}

	public void setRetain(Integer retain) {
		this.retain = retain;
	}

	public Date getLastMonitorUpdate() {
		return lastMonitorUpdate;
	}

	public void setLastMonitorUpdate(Date lastMonitorUpdate) {
		this.lastMonitorUpdate = lastMonitorUpdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public String getReplName() {
		return replName;
	}

	public void setReplName(String replName) {
		this.replName = replName;
	}

	public String getUpSince() {
		if (upTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm", Locale.US);
			return sdf.format(upTime);
		}

		return "";
	}

	@Override
	public String toString() {
		return "Server [name=" + name + ", hostname=" + hostname + ", port=" + port + ", username=" + username + ", password=" + password + ", method="
				+ method + "]";
	}

}
