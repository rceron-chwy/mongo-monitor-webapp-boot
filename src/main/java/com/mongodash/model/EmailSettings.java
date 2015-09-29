package com.mongodash.model;

import com.mongodash.Config;
import com.mongodash.Config.SETTINGS_KEY;

public class EmailSettings extends Settings {

	private String server;
	private String port;
	private String username;
	private String password;
	private String sender;
	private boolean ssl;

	@Override
	public SETTINGS_KEY getKey() {
		return Config.SETTINGS_KEY.email;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

}
