package com.mongodash.model;

public enum NotificationType {
	
	/* Backup */
	ALERT_CREATED("Alert Created", "New alert ({0}) created by {1} at {2}, on {3}"),
	ALERT_UPDATED("Alert Updated", "Alert {0} edited by {1} at {2}, on {3}"),
	ALERT_REMOVED("Alert Removed", "Alert {0} removed by {1} at {2}, on {3}"),
	
	/* Backup */
	BACKUP_CREATED("Backup Created", "New backup ({0}) created by {1} at {2}, on {3}"),
	BACKUP_UPDATED("Backup Updated", "Backup {0} edited by {1} at {2}, on {3}"),
	BACKUP_REMOVED("Backup Removed", "Backup {0} removed by {1} at {2}, on {3}"),
	BACKUP_EXECUTED("Backup Executed", "Backup {0} executed by {1} at {2}, on {3}"),
	
	/* Report */
	REPORT_CREATED("Report Created", "New report ({0}) created by {1} at {2}, on {3}"),
	REPORT_UPDATED("Report Updated", "Report {0} edited by {1} at {2}, on {3}"),
	REPORT_REMOVED("Report Removed", "Report {0} removed by {1} at {2}, on {3}"),
	REPORT_EXECUTED("Report Executed", "Report {0} executed by {1} at {2}, on {3}"),	
	
	/* User */
	USER_CREATED("User Created", "New user ({0}) created by {1} at {2}, on {3}"),
	USER_UPDATED("User Updated", "User {0} edited by {1} at {2}, on {3}"),
	USER_REMOVED("User Removed", "User {0} removed by {1} at {2}, on {3}"),
	USER_LOGGED_IN("User logged in", "Log in by {0} at {1}, on {2}"),
	USER_LOGGED_OUT("User logged out","Logout by {0} at {1}, on {2}"),
	
	/* Server */
	SERVER_CREATED("Server Created", "New server ({0}) created by {1} at {2}, on {3}"),
	SERVER_UPDATED("Server Updated", "Server {0} edited by {1} at {2}, on {3}"),
	SERVER_REMOVED("Server Removed", "Server {0} removed by {1} at {2}, on {3}"),
	SERVER_DOWN("Server down", "Server {0} is down"),
	INVALID_CREDENTAIS("Invalid Mongo Credentials", "Invalid Credentiais for Server {0}"),
	MONITOR_STARTED("Monitor Started", "Monitor for server {0} started by {1} at {2}, on {3}"),
	MONITOR_STOPPED("Monitor Stopped", "Monitor for server {0} stopped by {1} at {2}, on {3}"),
	
	/* Settings */
	MAIL_SETTINGS_CHANGED("Changes to Mail Settings", "Email settings changed by {0} at {1}, on {2}"),
	ALERTS_SETTINGS_CHANGED("Changes to Alerts Settings", "Alerts settings changed by {0} at {1}, on {2}"),
	NOTIFICATIONS_SETTINGS_CHANGED("Changes to Notification Settings", "Notification settings changed by {0} at {1}, on {2}"),
	LDAP_SETTINGS_CHANGED("Changes to LDAP Settings", "LDAP settings changed by {0} at {1}, on {2}");
	
	
	private NotificationType(String title, String text) {
		this.text = text;
		this.title = title;
	}
	
	private String text;
	private String title;
	
	public String getText() {
		return text;
	}
	
	public String getTitle() {
		return title;
	}
	
}
