package com.mongodash.event.handler;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.dao.mapper.ServerStatusDBObjectMapper;
import com.mongodash.event.MongoEventBus;
import com.mongodash.event.ServerStatusEvent;
import com.mongodash.model.Alert;
import com.mongodash.model.AlertField;
import com.mongodash.model.OperationType;
import com.mongodash.model.Server;
import com.mongodash.model.ServerStatus;
import com.mongodash.service.AlertsService;
import com.mongodash.service.SettingsService;
import com.mongodb.BasicDBObject;

@Component
public class ServerStatusEventHandler {
	
	@Autowired
	private AlertsService alertsService;
	
	@Autowired
	private SettingsService settingsService;
	
	@PostConstruct
	private void subscribeToService() {
		MongoEventBus.register(this);
	}	
	
	@Subscribe
	public void handleEvent(ServerStatusEvent serverStatusEvent) {

		if(settingsService.isConfigActive(SETTINGS_KEY.alerts)) {
			
			ServerStatus status = serverStatusEvent.getServerStatus();
			Server server = serverStatusEvent.getServer();
			
			//1. Get list of active Alerts
			List<Alert> alerts = alertsService.listActive();
			if(alerts != null && alerts.size() > 0) {
				for(Alert alert : alerts) {
					System.out.println("Running alert: " + alert.getText());
					if(isAlertValid(status, alert)) {
						processAlert(server, status, alert);
					}
				}
			}
			
		}
	}
	
	private boolean isAlertValid(ServerStatus serverStatus, Alert alert) {
		ServerStatusDBObjectMapper mapper = new ServerStatusDBObjectMapper();
		BasicDBObject dbo = (BasicDBObject) mapper.mapObject(serverStatus);
		Integer value = dbo.getInt(AlertField.valueOf(alert.getField()).getField());
		OperationType operation = OperationType.valueOf(alert.getOperation());
		Boolean result = false;
		
		switch(operation) {
			case EQUAL:
				result = value.equals(alert.getValue());
				break;
			case GREATER:
				result = value > alert.getValue();
				break;
			default:
				result = value < alert.getValue();
		}
		
		return result;
	}
	
	private void processAlert(Server server, ServerStatus serverStatus, Alert alert) {
		System.out.println("Alert processed");
	}
	
}
