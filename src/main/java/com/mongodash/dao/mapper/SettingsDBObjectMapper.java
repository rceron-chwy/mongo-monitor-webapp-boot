package com.mongodash.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodash.Config;
import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.dao.DBObjectMapper;
import com.mongodash.model.AlertSettings;
import com.mongodash.model.EmailSettings;
import com.mongodash.model.LdapSettings;
import com.mongodash.model.LicenseKeySettings;
import com.mongodash.model.NotificationSettings;
import com.mongodash.model.Settings;
import com.mongodash.util.PasswordUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class SettingsDBObjectMapper implements DBObjectMapper<Settings> {

	@Override
	public Settings mapDBObject(BasicDBObject object) {
		Settings settings = null;
		String _id = object.getString(Config.COMMON_FIELDS._id.name());
		if (SETTINGS_KEY.email.name().equals(_id)) {
			settings = new EmailSettings();
			if (object != null) {
				((EmailSettings) settings).setEnabled(object.getBoolean("enabled"));
				((EmailSettings) settings).setServer(object.getString("server"));
				((EmailSettings) settings).setPort(object.getString("port"));
				((EmailSettings) settings).setSender(object.getString("sender"));
				if (object.containsField("username") && object.containsField("password")) {
					((EmailSettings) settings).setUsername(object.getString("username"));
					((EmailSettings) settings).setPassword(PasswordUtil.decrypt(object.getString("password")));
				}
				((EmailSettings) settings).setSsl(object.getBoolean("ssl"));
			}
		} else if (SETTINGS_KEY.ldap.name().equals(_id)) {
			settings = new LdapSettings();
			if (object != null) {
				((LdapSettings) settings).setEnabled(object.getBoolean("enabled"));
				((LdapSettings) settings).setHost(object.getString("host"));
				((LdapSettings) settings).setPort(object.getString("port"));
				((LdapSettings) settings).setAdminDn(object.getString("adminDn"));
				((LdapSettings) settings).setAdminPassword(object.getString("adminPassword"));
				((LdapSettings) settings).setBaseDn(object.getString("baseDn"));
				((LdapSettings) settings).setLoginAttribute(object.getString("loginAttr"));
				((LdapSettings) settings).setSecure(object.getBoolean("secure"));
			}
		} else if (SETTINGS_KEY.alerts.name().equals(_id)) {
			settings = new AlertSettings();
			if (object != null) {
				((AlertSettings) settings).setEnabled(object.getBoolean("enabled"));
				((AlertSettings) settings).setEmailSubject(object.getString("emailSubject"));
				((AlertSettings) settings).setEmailRecipients(object.getString("emailRecipients"));
			}
		} else if (SETTINGS_KEY.notifications.name().equals(_id)) {
			settings = new NotificationSettings();
			if (object != null) {
				((NotificationSettings) settings).setEnabled(object.getBoolean("enabled"));
				((NotificationSettings) settings).setEmailEnabled(object.getBoolean("emailEnabled"));
				((NotificationSettings) settings).setEmailSubject(object.getString("emailSubject"));
				((NotificationSettings) settings).setEmailRecipients(object.getString("emailRecipients"));
				if (object.containsField("enabledNotifications")) {
					BasicDBList list = (BasicDBList) object.get("enabledNotifications");
					List<String> enabledNotifications = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						enabledNotifications.add((String) list.get(i));
					}
					((NotificationSettings) settings).setEnabledNotifications(enabledNotifications);
				}
			}
		} else if (SETTINGS_KEY.licenseKey.name().equals(_id)) {
			settings = new LicenseKeySettings();
			if (object != null) {
				((LicenseKeySettings) settings).setPrivateKey(object.getString("privateKey"));
				((LicenseKeySettings) settings).setPublicKey(object.getString("publicKey"));
			}
		}
		return settings;
	}

	@Override
	public DBObject mapObject(Settings object) {

		DBObject dbObject = new BasicDBObject();

		if (SETTINGS_KEY.email == object.getKey()) {
			EmailSettings es = (EmailSettings) object;
			dbObject.put(Config.COMMON_FIELDS._id.name(), SETTINGS_KEY.email.toString());
			dbObject.put("enabled", es.isEnabled());
			dbObject.put("server", es.getServer());
			dbObject.put("port", es.getPort());
			dbObject.put("sender", es.getSender());

			if (StringUtils.hasText(es.getUsername()) && StringUtils.hasText(es.getPassword())) {
				dbObject.put("username", es.getUsername());
				dbObject.put("password", PasswordUtil.encrypt(es.getPassword()));
			}
			dbObject.put("ssl", es.isSsl());
		} else if (SETTINGS_KEY.alerts == object.getKey()) {
			AlertSettings es = (AlertSettings) object;
			dbObject.put(Config.COMMON_FIELDS._id.name(), SETTINGS_KEY.alerts.toString());
			dbObject.put("enabled", es.isEnabled());
			dbObject.put("emailSubject", es.getEmailSubject());
			dbObject.put("emailRecipients", es.getEmailRecipients());
		} else if (SETTINGS_KEY.ldap == object.getKey()) {
			LdapSettings es = (LdapSettings) object;
			dbObject.put("_id", SETTINGS_KEY.ldap.toString());
			dbObject.put("enabled", es.isEnabled());
			dbObject.put("host", es.getHost());
			dbObject.put("port", es.getPort());
			dbObject.put("adminDn", es.getAdminDn());
			dbObject.put("adminPassword", es.getAdminPassword());
			dbObject.put("baseDn", es.getBaseDn());
			dbObject.put("loginAttr", es.getLoginAttribute());
			dbObject.put("secure", es.isSecure());
		} else if (SETTINGS_KEY.notifications == object.getKey()) {
			NotificationSettings es = (NotificationSettings) object;
			dbObject.put(Config.COMMON_FIELDS._id.name(), SETTINGS_KEY.notifications.toString());
			dbObject.put("enabled", es.isEnabled());
			dbObject.put("emailEnabled", es.isEmailEnabled());
			dbObject.put("emailSubject", es.getEmailSubject());
			dbObject.put("emailRecipients", es.getEmailRecipients());
			if (es.getEnabledNotifications() != null && es.getEnabledNotifications().size() > 0) {
				BasicDBList list = new BasicDBList();
				list.addAll(es.getEnabledNotifications());
				dbObject.put("enabledNotifications", list);
			}
		} else if (SETTINGS_KEY.licenseKey == object.getKey()) {
			LicenseKeySettings ls = (LicenseKeySettings) object;
			dbObject.put(Config.COMMON_FIELDS._id.name(), SETTINGS_KEY.licenseKey.toString());
			dbObject.put("privateKey", ls.getPrivateKey());
			dbObject.put("publicKey", ls.getPublicKey());
		}

		return dbObject;
	}

}
