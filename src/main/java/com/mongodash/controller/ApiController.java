package com.mongodash.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {
	
	static final Logger logger = LoggerFactory.getLogger(ApiController.class);	
	
	@RequestMapping(value = {"/agent/server-status"}, method = RequestMethod.POST)
	public @ResponseBody String serverStatus() {
		logger.info(request.getAttribute("server").toString());
		return "working - server-status";
	}	
	
	@RequestMapping(value = {"/agent/host-info"}, method = RequestMethod.POST)
	public @ResponseBody String hostInfo() {
		logger.info(request.getAttribute("server").toString());
		return "working - host-info";
	}
	
	@RequestMapping(value = {"/agent/list-databases"}, method = RequestMethod.POST)
	public @ResponseBody String listDatabases() {
		logger.info(request.getAttribute("server").toString());
		return "working - list-databases";
	}
	
	@RequestMapping(value = {"/agent/build-info"}, method = RequestMethod.POST)
	public @ResponseBody String buildInfo() {
		logger.info(request.getAttribute("server").toString());
		return "working - build-info";
	}	
	
	@RequestMapping(value = {"/agent/server-info"}, method = RequestMethod.POST)
	public @ResponseBody String serverInfo() {
		logger.info(request.getAttribute("server").toString());
		return "working - server-info";
	}		
	
	@RequestMapping(value = {"/agent/ping"}, method = RequestMethod.POST)
	public @ResponseBody String ping() {
		logger.info(request.getAttribute("server").toString());
		return "working - ping";
	}
	
	@RequestMapping(value = {"/agent/validate-key"}, method = RequestMethod.POST)
	public @ResponseBody String validate() {
		logger.info(request.getAttribute("server").toString());
		return "working - validate-key";
	}	

}
