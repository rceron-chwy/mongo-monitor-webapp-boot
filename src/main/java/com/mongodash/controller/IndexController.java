package com.mongodash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends BaseController {

	@RequestMapping(value = { "/", "/index" }, method = {RequestMethod.GET })
	public ModelAndView index() {

		ModelAndView mview = getModelView("index", "Dashboard", "small description");
		mview.addObject("menu", "index");	
		return mview;

	}

	@RequestMapping(value = { "/login" }, method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login() {

		ModelAndView mview = getModelView("login");
		return mview;

	}

}
