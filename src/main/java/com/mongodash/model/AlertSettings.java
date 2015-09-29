package com.mongodash.model;

import com.mongodash.Config.SETTINGS_KEY;

public class AlertSettings extends Settings {

	private String emailSubject;
	private String emailRecipients;
	
	@Override
	public SETTINGS_KEY getKey() {
		return SETTINGS_KEY.alerts;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailRecipients() {
		return emailRecipients;
	}

	public void setEmailRecipients(String emailRecipients) {
		this.emailRecipients = emailRecipients;
	}

}
