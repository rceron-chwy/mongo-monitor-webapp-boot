package com.mongodash.service;

import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.model.Response;
import com.mongodash.model.Settings;

public interface SettingsService {

	Settings getSettings(SETTINGS_KEY settingsKey);
	
	void saveSettings(Settings config);
	
	boolean isConfigActive(SETTINGS_KEY settingsKey);	
	
	Response testEmailSettings();
	
}
