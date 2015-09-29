package com.mongodash.event.handler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.event.MongoEventBus;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationSettings;
import com.mongodash.service.NotificationService;
import com.mongodash.service.SettingsService;

@Component
public class NotificationEventHandler {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private SettingsService settingsService;
	
	@PostConstruct
	private void subscribeToService() {
		MongoEventBus.register(this);
	}

	@Subscribe
	public void handleEvent(Notification event) {
		notificationService.save(event);
		
		NotificationSettings settings = (NotificationSettings) settingsService.getSettings(SETTINGS_KEY.notifications);
		
		if(settings != null && settings.isEnabled() && settings.isEmailEnabled()) {
			
			
			
		}
		
		
		
	}
	
}
