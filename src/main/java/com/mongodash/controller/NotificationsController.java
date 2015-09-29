package com.mongodash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodash.model.Notification;
import com.mongodash.model.NotificationResponse;
import com.mongodash.service.NotificationService;

@Controller
@RequestMapping("notifications")
public class NotificationsController extends BaseController {
	
	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = { "/last" }, method = RequestMethod.GET)
	@ResponseBody
	public NotificationResponse last() {
		NotificationResponse resp = new NotificationResponse();
		resp.setId(notificationService.getLastId());
		return resp;
	}	
	
	@RequestMapping(value = { "/next/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public NotificationResponse next(@PathVariable(value = "id") Integer notificationId) {
		NotificationResponse resp = new NotificationResponse();
		List<Notification> list = notificationService.getNotificationsAfterId(notificationId);
		if(list != null && list.size() > 0) {
			resp.setNotifications(list);
			resp.setId(list.get(list.size() - 1).getId());
		}
		else {
			resp.setId(notificationId);
		}
		return resp;
	}

}
