package com.mongodash.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodash.dump.DumpFormat;
import com.mongodash.model.Backup;
import com.mongodash.model.Breadcrumb;
import com.mongodash.model.Database;
import com.mongodash.model.DatabaseInfo;
import com.mongodash.model.Resource;
import com.mongodash.service.BackupService;
import com.mongodash.service.ServerService;

@Controller
@RequestMapping("backups")
public class BackupController extends BaseCRUDController<Backup> {

	@Autowired
	public BackupService backupService;
	
	@Autowired
	public ServerService serverService;	
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView mview = getModelView("list_backups", "MongoDB Backups", "List of Application Alerts");
		
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Backups", "backups/list"));
		mview.addObject("breadcrumbs", bds);		
		
		List<Backup> backups = backupService.list();
		mview.addObject("backups", backups);
		
		setupView(mview);
		return mview;
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView add() {
		return editView(new Backup());
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") Integer backupId) {

		Backup alert = backupService.getById(backupId);
		if (alert == null) return getModelView("forward:/404");

		return editView(alert);	
		
	}

	@RequestMapping(value = { "/remove/{id}" }, method = RequestMethod.GET)
	public ModelAndView remove(@PathVariable(value = "id") Integer backupId) {
		backupService.removeById(backupId);
		return new ModelAndView("redirect:/backups/list");
	}

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("backup") @Valid Backup backup, BindingResult result, 
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) return editView(backup);
		
		if(!canWrite(backup.getOutputFolder())) {
			ObjectError error = new ObjectError("backup", "The provided output folder needs write permission");
			result.addError(error);
			return editView(backup);
		}
		
		if(backup.getDatabases() == null || backup.getDatabases().size() == 0) {
			ObjectError error = new ObjectError("databases", "At least one database needs to be selected");
			result.addError(error);
			return editView(backup);			
		}
		
		if (backup.getId() == null)
			backupService.save(backup);

		else
			backupService.update(backup);

		redirectAttributes.addFlashAttribute("backup", backup);
		return new ModelAndView("redirect:/backups/list");
		
	}
	
	private ModelAndView editView(Backup backup) {
		
		ModelAndView mview = getModelView("edit_backup");
		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Hosts"));
		bds.add(new Breadcrumb("Backups", "backups/list"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("backup", backup);
		
		mview.addObject("servers", serverService.list());
		
		if(backup.getServerId() != null) {
			DatabaseInfo dbInfo = serverService.getDatabaseInfo(backup.getServerId());
			if(dbInfo != null) {
				mview.addObject("dbs", dbInfo.getDatabase());
			}
			else {
				List<Database> dbs = new ArrayList<Database>();
				mview.addObject("dbs", dbs);
			}
		}
		else {
			List<Database> dbs = new ArrayList<Database>();
			mview.addObject("dbs", dbs);
		}
		
		if (backup.getId() != null && backup.getId() > 0) {
			mview.addObject("title", "Edit Backup");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("Edit Backup", "backups/edit/" + backup.getId()));
		} else {
			mview.addObject("title", "New Backup");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("New Backup", "backups/new"));
		}		
		
		mview.addObject("dumpFormats", DumpFormat.values());
		setupView(mview);
		return mview;
		
	}
	
	@RequestMapping(value = { "/run/{id}" }, method = RequestMethod.GET)
	public ModelAndView run(@PathVariable(value = "id") Integer backupId) {
		backupService.run(backupId);
		return new ModelAndView("redirect:/backups/list");
	}
	
	@RequestMapping(value = { "/dbs/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Database> dbs(@PathVariable(value = "id") Integer serverId) {
		
		DatabaseInfo dbInfo = serverService.getDatabaseInfo(serverId);
		if(dbInfo != null) {
			return dbInfo.getDatabase();
		}
		
		return new ArrayList<Database>();
	}	
	
	private void setupView(ModelAndView mview) {
		
		mview.addObject("menu", "hosts");
		mview.addObject("subMenu", "backups");
		
		List<Resource> pageStyles = new ArrayList<Resource>();
		pageStyles.add(new Resource("css/jquery.multi-select.css"));
		mview.addObject("styles", pageStyles);		
		
		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("js/jquery.multi-select.js"));
		mview.addObject("plugins", pagePlugins);
		
		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_forms.js"));
		pageScripts.add(new Resource("js/app_backups.js"));
		mview.addObject("scripts", pageScripts);	
		
		List<String> modules = new ArrayList<String>();
		modules.add("Forms");
		modules.add("Backups");
		mview.addObject("initModules", modules);		
		
	}
	
	private boolean canWrite(String folder) {
		
		File f = new File(folder);
		if(f.canWrite()) {
			return true;
		}
		return false;
	}

}
