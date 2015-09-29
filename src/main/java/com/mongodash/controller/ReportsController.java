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

import com.mongodash.model.AggregationGroup;
import com.mongodash.model.AggregationRange;
import com.mongodash.model.AggregationType;
import com.mongodash.model.AlertField;
import com.mongodash.model.Breadcrumb;
import com.mongodash.model.Report;
import com.mongodash.model.Resource;
import com.mongodash.service.ReportService;
import com.mongodash.service.ServerService;

@Controller
@RequestMapping("reports")
public class ReportsController extends BaseController {
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	ServerService serverService;
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mview = getModelView("list_reports", "Reports", 
				"List of available reports");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Reports", "reports/list"));
		mview.addObject("breadcrumbs", bds);

		List<Resource> styles = new ArrayList<Resource>();
		styles.add(new Resource("css/table-responsive.css"));
		mview.addObject("styles", styles);

		List<Report> reports = reportService.list();
		mview.addObject("reports", reports);

		setupUI(mview);

		return mview;
	}	
	
	@RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") Integer reportId)	{
		
		Report report = reportService.getById(reportId);
		if (report == null)
			return getModelView("forward:/404");
		
		ModelAndView mview = getModelView("edit_report");
				
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Reports", "reports/list"));
		mview.addObject("breadcrumbs", bds);
		
		mview.addObject("report", report);
		mview.addObject("types", AlertField.values());
		mview.addObject("aggregationTypes", AggregationType.values());
		mview.addObject("aggregationRanges", AggregationRange.values());
		mview.addObject("aggregationGroups", AggregationGroup.values());
		mview.addObject("servers", serverService.list());
		
		if(reportId != null) {
			mview.addObject("title", "Edit Report");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("Edit Report", "reports/edit/" + reportId));
		}
		else {
			mview.addObject("title", "New Report");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("New Report", "reports/new"));
		}	
		
		List<Resource> initJs = new ArrayList<Resource>();
		initJs.add(new Resource("Servers.validateForm();"));
		mview.addObject("inits", initJs);		
		
		setupUI(mview);
		
		return mview;
	}
	
	@RequestMapping(value = {"/new"}, method = RequestMethod.GET)
	public ModelAndView newUser()	{
		return add(null);
	}	
	
	private ModelAndView add(Report report) {
		
		ModelAndView mview = getModelView("edit_report");
		
		if (report == null) report = new Report();
		
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Reports", "reports/list"));
		mview.addObject("breadcrumbs", bds);

		mview.addObject("title", "New Report");
		mview.addObject("description", "test");
		bds.add(new Breadcrumb("New Report", "reports/new"));	
		
		List<Resource> initJs = new ArrayList<Resource>();
		initJs.add(new Resource("Servers.validateForm();"));
		mview.addObject("inits", initJs);	

		mview.addObject("report", report);
		mview.addObject("types", AlertField.values());
		mview.addObject("aggregationTypes", AggregationType.values());
		mview.addObject("aggregationRanges", AggregationRange.values());
		mview.addObject("aggregationGroups", AggregationGroup.values());
		mview.addObject("servers", serverService.list());
		
		setupUI(mview);		
		return mview;
	}
	
	@RequestMapping(value = {"/save"}, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("report") @Valid Report report, BindingResult result, RedirectAttributes redirectAttributes)	{
		
		if (result.hasErrors()) {
			return add(null);
		}
		
		if(report.getId() == null) {
			reportService.save(report);
		}
		else {
			reportService.update(report);
		}
		
		redirectAttributes.addFlashAttribute("report", report);
		return new ModelAndView("redirect:/reports/list");
	}
	
	@RequestMapping(value = {"/remove/{id}"}, method = RequestMethod.GET)
	public ModelAndView remove(@PathVariable(value = "id") Integer reportId) {
		
		reportService.removeById(reportId);
		return new ModelAndView("redirect:/reports/list");
	}	
	
	@RequestMapping(value = {"/run/{id}"}, method = RequestMethod.GET)
	public ModelAndView run(@PathVariable(value = "id") Integer reportId)	{
		
		ModelAndView mview = getModelView("report_result", "Result", 
				"List of available reports");
		
		reportService.runReport(reportId);
		
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Reports", "reports/list"));
		bds.add(new Breadcrumb("Report Results", "reports/run/" + reportId));
		mview.addObject("breadcrumbs", bds);

		mview.addObject("title", "Report Results");
		mview.addObject("description", "test");			
		
		setupUI(mview);
		return mview;
		
	}
	
	private void setupUI(ModelAndView modelView) {
		
		modelView.addObject("menu", "hosts");
		modelView.addObject("subMenu", "reports");
		
		List<Resource> pageStyles = new ArrayList<Resource>();
		pageStyles.add(new Resource("css/jquery.multi-select.css"));
		modelView.addObject("styles", pageStyles);		
		
		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("js/jquery.multi-select.js"));
		modelView.addObject("plugins", pagePlugins);
		
		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_forms.js"));
		modelView.addObject("scripts", pageScripts);	
		
		List<String> modules = new ArrayList<String>();
		modules.add("Forms");
		modelView.addObject("initModules", modules);		
		
	}	
	
}
