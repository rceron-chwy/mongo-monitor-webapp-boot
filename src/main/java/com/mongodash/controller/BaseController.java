package com.mongodash.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mongodash.model.Breadcrumb;
import com.mongodash.model.UIAction;

public abstract class BaseController {

	@Autowired
	protected HttpServletRequest request;
	

	// ~ Commom Request Mappings ---------------------------------------
	@RequestMapping(value = { "404" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String fourOwFour() {
		return "404";
	}

	@RequestMapping(value = { "500" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String fiveHundred() {
		return "500";
	}
	
	@RequestMapping(value = { "403" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String fourOwThree() {
		return "403";
	}	
	

	// ~ Commom Methods ------------------------------------------------

	/**
	 * @param model
	 * @return
	 */
	protected ModelAndView getModelView(String model) {
		ModelAndView mview = new ModelAndView(model);
		return mview;
	}

	/**
	 * @param model
	 * @param title
	 * @return
	 */
	protected ModelAndView getModelView(String model, String title) {
		ModelAndView mview = new ModelAndView(model);
		mview.addObject("title", title);
		return mview;
	}

	/**
	 * @param model
	 * @param title
	 * @param description
	 * @return
	 */
	protected ModelAndView getModelView(String model, String title, String description) {
		ModelAndView mview = new ModelAndView(model);
		mview.addObject("title", title);
		mview.addObject("description", description);
		return mview;
	}

	/**
	 * @param model
	 * @param title
	 * @param description
	 * @param breadcrumbs
	 * @return
	 */
	protected ModelAndView getModelView(String model, String title, String description, Breadcrumb... breadcrumbs) {
		ModelAndView mview = new ModelAndView(model);
		mview.addObject("title", title);
		mview.addObject("description", description);
		mview.addObject("breadcrumbs", breadcrumbs);
		return mview;
	}

	/**
	 * @param model
	 * @param title
	 * @param description
	 * @param actions
	 * @return
	 */
	protected ModelAndView getModelView(String model, String title, String description, UIAction... actions) {
		ModelAndView mview = new ModelAndView(model);
		mview.addObject("title", title);
		mview.addObject("description", description);
		mview.addObject("actions", actions);
		return mview;
	}

	// ~ Redirect

	protected ModelAndView redirectTo(String url) {
		RedirectView view = new RedirectView(url);
		view.setExposeModelAttributes(false);
		return new ModelAndView(view);
	}
}
