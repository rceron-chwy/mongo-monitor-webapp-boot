package com.mongodash.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mongodash.model.Breadcrumb;
import com.mongodash.model.Resource;
import com.mongodash.model.Server;
import com.mongodash.service.ServerService;

@Controller
@RequestMapping("hosts")
public class HostsController extends BaseController {

	@Autowired
	ServerService serverService;
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mview = getModelView("list_hosts", "Application Alerts", 
				"List of Application Alerts");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Live Stats", "hosts/list"));
		mview.addObject("breadcrumbs", bds);

		List<Resource> styles = new ArrayList<Resource>();
		styles.add(new Resource("css/table-responsive.css"));
		mview.addObject("styles", styles);

		List<Server> servers = serverService.list();
		mview.addObject("servers", servers);

		setupUI(mview);

		return mview;
	}	
	
	private void setupUI(ModelAndView modelView) {
		
		modelView.addObject("menu", "hosts");
		modelView.addObject("subMenu", "servers");
		
		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("js/bootstrap-switch.js"));
		modelView.addObject("plugins", pagePlugins);
		
		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_hosts.js"));
		pageScripts.add(new Resource("js/jquery.validate.min.js"));
		pageScripts.add(new Resource("js/jquery.timeago.js"));
		modelView.addObject("scripts", pageScripts);
		
		List<String> modules = new ArrayList<String>();
		modules.add("Hosts");
		modelView.addObject("initModules", modules);		
		
	}	
	
}
