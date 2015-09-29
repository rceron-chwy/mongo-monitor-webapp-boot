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
import com.mongodash.model.Resource;
import com.mongodash.model.Server;
import com.mongodash.model.StatsResponse;
import com.mongodash.service.ServerService;
import com.mongodash.service.StatsService;

@Controller
@RequestMapping("stats")
public class StatsController extends BaseController {

	@Autowired
	StatsService statsService;
	
	@Autowired
	ServerService serverService;
	
	@RequestMapping(value = {"/list/{serverId}"}, method = RequestMethod.GET)
	@ResponseBody
	public StatsResponse list(@PathVariable(value = "serverId") Integer serverId)	{
		
		StatsResponse response = new StatsResponse();
		response.setStats(statsService.getLastServerStatus(serverId));
		return response;
	}
	
	@RequestMapping(value = {"/live/{serverId}"}, method = RequestMethod.GET)
	public ModelAndView live(@PathVariable(value = "serverId") Integer serverId)	{
		
		Server server = serverService.getById(serverId);
		if (server == null)
			return getModelView("forward:/404");		
		
		ModelAndView mview = getModelView("live_stats", "Live Stats", "small description");
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Servers", "hosts/list"));
		bds.add(new Breadcrumb(server.getName(), "stats/live/" + server.getId()));
		mview.addObject("breadcrumbs", bds);
		
		mview.addObject("server", server);
		
		setupUI(mview);

		return mview;
	}	
	
	private void setupUI(ModelAndView modelView) {
		
		modelView.addObject("menu", "hosts");
		modelView.addObject("subMenu", "servers");
		
		List<Resource> pageStyles = new ArrayList<Resource>();
		pageStyles.add(new Resource("css/morris.css"));
		modelView.addObject("styles", pageStyles);		
		
		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("js/bootstrap-switch.js"));
		pagePlugins.add(new Resource("js/jquery.flot.js"));
		pagePlugins.add(new Resource("js/jquery.flot.resize.js"));
		pagePlugins.add(new Resource("js/jquery.flot.pie.js"));
		pagePlugins.add(new Resource("js/jquery.flot.stack.js"));
		pagePlugins.add(new Resource("js/jquery.flot.crosshair.js"));
		pagePlugins.add(new Resource("js/morris.min.js"));
		pagePlugins.add(new Resource("js/raphael-min.js"));
		modelView.addObject("plugins", pagePlugins);
		
		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_livestats.js"));
		pageScripts.add(new Resource("js/jquery.validate.min.js"));
		modelView.addObject("scripts", pageScripts);

		List<String> modules = new ArrayList<String>();
		modules.add("LiveStats");
		modelView.addObject("initModules", modules);		
		
	}
	
}
