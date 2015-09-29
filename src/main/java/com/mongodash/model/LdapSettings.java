package com.mongodash.model;

import com.mongodash.Config.SETTINGS_KEY;

public class LdapSettings extends Settings {

	private String host;
	private String port;
	private boolean secure;
	private String adminDn;
	private String adminPassword;
	private String baseDn;
	private String loginAttribute;

	@Override
	public SETTINGS_KEY getKey() {
		return SETTINGS_KEY.ldap;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getAdminDn() {
		return adminDn;
	}

	public void setAdminDn(String adminDn) {
		this.adminDn = adminDn;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getBaseDn() {
		return baseDn;
	}

	public void setBaseDn(String baseDn) {
		this.baseDn = baseDn;
	}

	public String getLoginAttribute() {
		return loginAttribute;
	}

	public void setLoginAttribute(String loginAttribute) {
		this.loginAttribute = loginAttribute;
	}

}