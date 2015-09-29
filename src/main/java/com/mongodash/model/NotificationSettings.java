package com.mongodash.model;

import java.util.List;

import com.mongodash.Config.SETTINGS_KEY;

public class NotificationSettings extends Settings {

	private boolean emailEnabled;
	private String emailSubject;
	private String emailRecipients;
	private List<String> enabledNotifications;

	@Override
	public SETTINGS_KEY getKey() {
		return SETTINGS_KEY.notifications;
	}

	public boolean isEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
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

	public List<String> getEnabledNotifications() {
		return enabledNotifications;
	}

	public void setEnabledNotifications(List<String> enabledNotifications) {
		this.enabledNotifications = enabledNotifications;
	}

}
