package com.mongodash.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodash.model.Breadcrumb;
import com.mongodash.model.Notification;
import com.mongodash.model.Resource;
import com.mongodash.service.AlertsService;
import com.mongodash.service.NotificationService;

@Controller
@RequestMapping("view")
public class ViewController extends BaseController {

	@Autowired
	AlertsService alertService;
	
	@Autowired
	NotificationService notificationService;
	
	
	@RequestMapping(value = { "/alerts" }, method = RequestMethod.GET)
	public ModelAndView alerts() {
		ModelAndView mview = getModelView("view_alerts", "MongoDB Backups", "List of Application Alerts");
		mview.addObject("menu", "application");
		mview.addObject("subMenu", "alerts");
		
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Aplication"));
		bds.add(new Breadcrumb("View Alerts", "view/alerts"));
		mview.addObject("breadcrumbs", bds);
		
		return mview;
	}

	@RequestMapping(value = { "/alerts/page/{page}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Notification> alerts_page(@PathVariable(value = "page") Integer page) {
		List<Notification> nots = notificationService.getNotificationsByPage(page);		
		return nots;
	}	
	
	@RequestMapping(value = { "/notifications" }, method = RequestMethod.GET)
	public ModelAndView notifications() {
		
		ModelAndView mview = getModelView("view_notifications", "MongoDash Notifications", "List of notifications created by MongoDash");
		mview.addObject("menu", "application");
		mview.addObject("subMenu", "notifications");
		
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Application"));
		bds.add(new Breadcrumb("View Notifications", "view/notifications"));
		mview.addObject("breadcrumbs", bds);
		
		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_view_notifications.js"));
		mview.addObject("scripts", pageScripts);
		
		List<String> modules = new ArrayList<String>();
		modules.add("ViewNotifications");
		mview.addObject("initModules", modules);		
				
		return mview;
	}
	
	@RequestMapping(value = { "/notifications/page/{page}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Notification> notifications_page(@PathVariable(value = "page") Integer page) {
		List<Notification> nots = notificationService.getNotificationsByPage(page);		
		return nots;
	}
	
	@RequestMapping(value = { "/logs" }, method = RequestMethod.GET)
	public ModelAndView logs() {
		ModelAndView mview = getModelView("view_logs", "MongoDB Backups", "List of Application Alerts");
		mview.addObject("menu", "administration");
		mview.addObject("subMenu", "logs");
		
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Administration"));
		bds.add(new Breadcrumb("Logs", "view/logs"));
		mview.addObject("breadcrumbs", bds);
		
		return mview;
	}
	
	@RequestMapping(value = { "/logs/page/{page}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Notification> logs_page(@PathVariable(value = "page") Integer page) {
		List<Notification> nots = notificationService.getNotificationsByPage(page);		
		return nots;
	}	
	
}
