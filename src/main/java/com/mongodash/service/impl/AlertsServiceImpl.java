package com.mongodash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodash.dao.AlertDao;
import com.mongodash.event.MongoEventBus;
import com.mongodash.model.Alert;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.service.AlertsService;

@Service
public class AlertsServiceImpl implements AlertsService {

	@Autowired
	private AlertDao alertsDao;

	@Override
	public List<Alert> list() {
		return alertsDao.list();
	}

	@Override
	public List<Alert> listActive() {
		return alertsDao.getActive();
	}

	@Override
	public Alert getById(Integer id) {
		return alertsDao.getById(id);
	}

	@Override
	public void save(Alert t) {
		alertsDao.save(t);
		MongoEventBus.post(new Notification(NotificationType.ALERT_CREATED, t.getText()));
	}

	@Override
	public void update(Alert t) {
		alertsDao.update(t);
		MongoEventBus.post(new Notification(NotificationType.ALERT_UPDATED, t.getText()));
	}

	@Override
	public void remove(Alert t) {
		alertsDao.remove(t);
		MongoEventBus.post(new Notification(NotificationType.ALERT_REMOVED, t.getText()));
	}

	@Override
	public void removeById(Integer id) {
		Alert alert = getById(id);
		if(alert != null) {
			remove(alert);
		}
	}

}
