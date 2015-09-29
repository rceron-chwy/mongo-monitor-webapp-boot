package com.mongodash.model;

import com.mongodash.Config.SETTINGS_KEY;

public abstract class Settings {

	private boolean enabled;
	
	public abstract SETTINGS_KEY getKey();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}
	
}
