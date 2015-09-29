package com.mongodash.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodash.dao.NotificationDao;
import com.mongodash.model.Notification;
import com.mongodash.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private SimpleDateFormat hourFormater = new SimpleDateFormat("HH:mm:ss", Locale.US);
	private SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	@Autowired
	private NotificationDao notificationDao;

	@Override
	public List<Notification> list() {
		return notificationDao.list();
	}

	@Override
	public Notification getById(Integer id) {
		return notificationDao.getById(id);
	}

	@Override
	public void save(Notification t) {

		// TODO qdo o spring security estiver ok, substituir o meu nome pelo
		// nome do usuario que estiver logado

		String text = null;
		Notification not = null;

		switch (t.getType()) {
		case SERVER_CREATED:
		case SERVER_UPDATED:
		case SERVER_REMOVED:
		case MONITOR_STARTED:
		case MONITOR_STOPPED:
			text = MessageFormat.format(t.getType().getText(), t.getObject(), "Thiago", hourFormater.format(new Date()),
					dateFormater.format(new Date()));
			break;
		case BACKUP_CREATED:
		case BACKUP_UPDATED:
		case BACKUP_REMOVED:
		case BACKUP_EXECUTED:
			text = MessageFormat.format(t.getType().getText(), t.getObject(), "Thiago", hourFormater.format(new Date()),
					dateFormater.format(new Date()));
			break;
		case REPORT_CREATED:
		case REPORT_UPDATED:
		case REPORT_REMOVED:
		case REPORT_EXECUTED:
			text = MessageFormat.format(t.getType().getText(), t.getObject(), "Thiago", hourFormater.format(new Date()),
					dateFormater.format(new Date()));
			break;
		case ALERT_CREATED:
		case ALERT_UPDATED:
		case ALERT_REMOVED:
			text = MessageFormat.format(t.getType().getText(), t.getObject(), "Thiago", hourFormater.format(new Date()),
					dateFormater.format(new Date()));
			break;
		case USER_CREATED:
		case USER_REMOVED:
		case USER_UPDATED:
			text = MessageFormat.format(t.getType().getText(), t.getObject(), "Thiago", hourFormater.format(new Date()),
					dateFormater.format(new Date()));
			break;
		case USER_LOGGED_IN:
		case USER_LOGGED_OUT:
		case MAIL_SETTINGS_CHANGED:
		case ALERTS_SETTINGS_CHANGED:
		case NOTIFICATIONS_SETTINGS_CHANGED:
		case LDAP_SETTINGS_CHANGED:
			text = MessageFormat.format(t.getType().getText(), t.getObject(), hourFormater.format(new Date()), dateFormater.format(new Date()));
			break;
		case SERVER_DOWN:
		case INVALID_CREDENTAIS:
			text = MessageFormat.format(t.getType().getText(), t.getObject());
			break;

		}

		if (text != null) {
			not = new Notification(t.getType());
			not.setText(text);
			notificationDao.save(not);
		}
	}

	@Override
	public void update(Notification t) {
		notificationDao.update(t);
	}

	@Override
	public void remove(Notification t) {
		notificationDao.remove(t);
	}

	@Override
	public void removeById(Integer id) {
		Notification notification = getById(id);
		if(notification != null) {
			remove(notification);
		}
	}

	@Override
	public List<Notification> getNotificationsAfterId(Integer id) {
		return notificationDao.getNotificationsAfterId(id);
	}

	@Override
	public Integer getLastId() {
		return notificationDao.getLastId();
	}

	@Override
	public List<Notification> getNotificationsByPage(Integer page) {
		
		int total = 25;
		int skip = (page-1) * total;
		
		return notificationDao.getLastNotifications(total, skip);
	}
	
	
}