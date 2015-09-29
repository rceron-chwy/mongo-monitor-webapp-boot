package com.mongodash.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodash.model.Alert;
import com.mongodash.model.AlertField;
import com.mongodash.model.Breadcrumb;
import com.mongodash.model.OperationType;
import com.mongodash.model.Resource;
import com.mongodash.service.AlertsService;

@Controller
@RequestMapping("alerts")
public class AlertsController extends BaseCRUDController<Alert> {

	@Autowired
	AlertsService alertsService;

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mview = getModelView("list_alerts", "Application Alerts", "List of Application Alerts");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Alerts", "alerts/list"));
		mview.addObject("breadcrumbs", bds);

		List<Resource> styles = new ArrayList<Resource>();
		styles.add(new Resource("css/table-responsive.css"));
		mview.addObject("styles", styles);

		List<Alert> alerts = alertsService.list();
		mview.addObject("alerts", alerts);

		setupUI(mview);

		return mview;
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") Integer alertId) {

		Alert alert = alertsService.getById(alertId);
		if (alert == null) return getModelView("forward:/404");

		return editView(alert);
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView add() {
		return editView(new Alert());
	}

	private ModelAndView editView(Alert alert) {

		ModelAndView mview = getModelView("edit_alert");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Alerts", "alerts/list"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("alert", alert);

		if (alert.getId() != null && alert.getId() > 0) {
			mview.addObject("title", "Edit Alert");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("Edit Alert", "alerts/edit/" + alert.getId()));
		} else {
			mview.addObject("title", "New Alert");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("New Alert", "alerts/new"));
		}

		List<Resource> initJs = new ArrayList<Resource>();
		initJs.add(new Resource("Servers.validateForm();"));
		mview.addObject("inits", initJs);

		mview.addObject("fields", AlertField.values());
		mview.addObject("operations", OperationType.values());

		setupUI(mview);
		return mview;
	}

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("alert") @Valid Alert alert, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) return editView(alert);

		if (alert.getId() == null)
			alertsService.save(alert);

		else
			alertsService.update(alert);

		redirectAttributes.addFlashAttribute("alert", alert);
		return new ModelAndView("redirect:/alerts/list");
	}

	@RequestMapping(value = { "/remove/{id}" }, method = RequestMethod.GET)
	public ModelAndView remove(@PathVariable(value = "id") Integer alertId) {

		alertsService.removeById(alertId);
		return new ModelAndView("redirect:/alerts/list");
	}

	private void setupUI(ModelAndView modelView) {

		modelView.addObject("menu", "hosts");
		modelView.addObject("subMenu", "alerts");

		List<Resource> pagePlugins = new ArrayList<Resource>();
		modelView.addObject("plugins", pagePlugins);

		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/jquery.validate.min.js"));
		modelView.addObject("scripts", pageScripts);
	}

}
