package com.mongodash.model;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class RememberMeToken {

	private String username;
	private String series;
	private String tokenValue;
	private Date date;
	
	public static enum fields {
		username, series, tokenValue, date;
	}

	public RememberMeToken() {
	}

	public RememberMeToken(PersistentRememberMeToken token) {
		this.series = token.getSeries();
		this.username = token.getUsername();
		this.tokenValue = token.getTokenValue();
		this.date = token.getDate();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
