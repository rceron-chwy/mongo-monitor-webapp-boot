package com.mongodash;

public class Config {

	public static enum MONGO_COLLECTION {
		sequences, servers, users, serverStats, admin, settings, reports, serverInfo, alerts, notifications, rememberMe, backup
	}

	public static enum SETTINGS_KEY {
		alerts, notifications, email, ldap, licenseKey
	}

	public static enum HTTP_SCHEME {
		http, https
	}

	public static enum COMMON_FIELDS {
		_id, name, active, updated, enabled;
	}

	public static enum STATUS {
		OK
	}
}
