package com.mongodash.model;

import com.mongodash.Config.SETTINGS_KEY;

public class LicenseKeySettings extends Settings {

	private String privateKey;
	private String publicKey;

	@Override
	public SETTINGS_KEY getKey() {
		return SETTINGS_KEY.licenseKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
