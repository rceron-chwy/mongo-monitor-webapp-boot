package com.mongodash.dao;

import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.model.Settings;

public interface SettingsDao {
	
	Settings getSettings(SETTINGS_KEY settingsKey);
	
	void saveSettings(Settings settings);
	
	boolean isConfigActive(SETTINGS_KEY settingsKey);

}
