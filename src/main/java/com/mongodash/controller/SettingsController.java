package com.mongodash.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.exception.HttpRequestHandlerException;
import com.mongodash.model.AlertSettings;
import com.mongodash.model.Breadcrumb;
import com.mongodash.model.EmailSettings;
import com.mongodash.model.LdapSettings;
import com.mongodash.model.LicenseKeySettings;
import com.mongodash.model.NotificationSettings;
import com.mongodash.model.NotificationType;
import com.mongodash.model.Resource;
import com.mongodash.model.Response;
import com.mongodash.service.SettingsService;

@Controller
@RequestMapping("settings")
public class SettingsController extends BaseController {

	@Autowired
	SettingsService settingsService;
	
	//TEST
	@RequestMapping(value = { "/aaaa" }, method = RequestMethod.GET)
	public ModelAndView aaaa() throws HttpRequestHandlerException {
		throw new HttpRequestHandlerException("");
	}
	

	// ~ Alerts ------------------------------------------------------------
	// ---------------------------------------------------------------------
	@RequestMapping(value = { "/alerts" }, method = RequestMethod.GET)
	public ModelAndView configAlerts() {

		ModelAndView mview = getModelView("settings_alerts", "Alerts settings", "Settings for Alerts");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Settings"));
		bds.add(new Breadcrumb("Alerts", "alerts/config"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("subMenu", "alerts");

		AlertSettings settings = (AlertSettings) settingsService.getSettings(SETTINGS_KEY.alerts);
		if (settings == null) settings = new AlertSettings();
		mview.addObject("settings", settings);

		setupUI(mview);
		return mview;

	}
	
	@RequestMapping(value = { "/alerts/save" }, method = RequestMethod.POST)
	public ModelAndView saveAlerts(@ModelAttribute("settings") @Valid AlertSettings alertSettings, RedirectAttributes redirectAttributes) {

		settingsService.saveSettings(alertSettings);
		redirectAttributes.addFlashAttribute("settings", alertSettings);
		redirectAttributes.addFlashAttribute("saved", Boolean.TRUE);
		return new ModelAndView("redirect:/settings/alerts");
	}

	// ~ License  ----------------------------------------------------------
	// ---------------------------------------------------------------------
	
	@RequestMapping(value = { "/license/save" }, method = RequestMethod.POST)
	public ModelAndView saveLicenseKey(@ModelAttribute("settings") @Valid LicenseKeySettings settings) {

		settingsService.saveSettings(settings);
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value = { "/license" }, method = RequestMethod.GET)
	public ModelAndView configLicense() {
		ModelAndView mview = getModelView("settings_license");
		mview.addObject("settings", new LicenseKeySettings());
		return mview;
	}

	// ~ Email ------------------------------------------------------------
	// ---------------------------------------------------------------------

	@RequestMapping(value = { "/email" }, method = RequestMethod.GET)
	public ModelAndView configEmail() {

		ModelAndView mview = getModelView("settings_email", "Email settings", "Settings used by the application to send alerts/notifications via email.");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Settings"));
		bds.add(new Breadcrumb("Email", "email/config"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("subMenu", "email");

		EmailSettings settings = (EmailSettings) settingsService.getSettings(SETTINGS_KEY.email);
		if (settings == null) settings = new EmailSettings();
		mview.addObject("settings", settings);

		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_mail.js"));
		mview.addObject("scripts", pageScripts);

		List<String> modules = new ArrayList<String>();
		modules.add("Mail");
		mview.addObject("initModules", modules);

		setupUI(mview);
		return mview;
	}

	@RequestMapping(value = { "/email/save" }, method = RequestMethod.POST)
	public ModelAndView saveEmail(@ModelAttribute("settings") @Valid EmailSettings emailSettings, RedirectAttributes redirectAttributes) {

		settingsService.saveSettings(emailSettings);
		redirectAttributes.addFlashAttribute("settings", emailSettings);
		redirectAttributes.addFlashAttribute("saved", Boolean.TRUE);
		return new ModelAndView("redirect:/settings/email");

	}

	// ~ Notifications -----------------------------------------------------
	// ---------------------------------------------------------------------

	@RequestMapping(value = { "/notifications" }, method = RequestMethod.GET)
	public ModelAndView configNotifications() {

		ModelAndView mview = getModelView("settings_notifications", "Notifications settings",
				"Settings for displaying on screen notifications and/or sending notifications via email.");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Settings"));
		bds.add(new Breadcrumb("Notifications", "notifications/config"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("subMenu", "notifications");

		NotificationSettings settings = (NotificationSettings) settingsService.getSettings(SETTINGS_KEY.notifications);
		if (settings == null) settings = new NotificationSettings();
		mview.addObject("settings", settings);
		mview.addObject("types", NotificationType.values());

		List<Resource> pageStyles = new ArrayList<Resource>();
		pageStyles.add(new Resource("assets/jquery-multi-select/css/multi-select.css"));
		mview.addObject("styles", pageStyles);

		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("assets/jquery-multi-select/js/jquery.multi-select.js"));
		mview.addObject("plugins", pagePlugins);

		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_forms.js"));
		mview.addObject("scripts", pageScripts);

		List<String> modules = new ArrayList<String>();
		modules.add("Forms");
		mview.addObject("initModules", modules);

		setupUI(mview);
		return mview;

	}

	@RequestMapping(value = { "/notifications/save" }, method = RequestMethod.POST)
	public ModelAndView saveNotifications(@ModelAttribute("settings") @Valid NotificationSettings notificationSettings,
			RedirectAttributes redirectAttributes) {

		settingsService.saveSettings(notificationSettings);
		redirectAttributes.addFlashAttribute("settings", notificationSettings);
		redirectAttributes.addFlashAttribute("saved", Boolean.TRUE);
		return new ModelAndView("redirect:/settings/notifications");
	}

	// ~ LDAP --------------------------------------------------------------
	// ---------------------------------------------------------------------

	@RequestMapping(value = { "/ldap" }, method = RequestMethod.GET)
	public ModelAndView configLdap() {

		ModelAndView mview = getModelView("settings_ldap", "LDAP settings", "Settings used by the application to authenticate users.");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Settings"));
		bds.add(new Breadcrumb("LDAP", "ldap/config"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("subMenu", "ldap");

		LdapSettings settings = (LdapSettings) settingsService.getSettings(SETTINGS_KEY.ldap);
		if (settings == null) settings = new LdapSettings();
		mview.addObject("settings", settings);
		setupUI(mview);
		return mview;

	}

	@RequestMapping(value = { "/ldap/save" }, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("settings") @Valid LdapSettings ldapSettings, RedirectAttributes redirectAttributes) {

		settingsService.saveSettings(ldapSettings);
		redirectAttributes.addFlashAttribute("settings", ldapSettings);
		redirectAttributes.addFlashAttribute("saved", Boolean.TRUE);
		return new ModelAndView("redirect:/settings/ldap");

	}

	// ~ Email Test --------------------------------------------------------
	// ---------------------------------------------------------------------

	@RequestMapping(value = { "/email/test" }, method = RequestMethod.GET)
	@ResponseBody
	public Response emailTest() {

		Response resp = settingsService.testEmailSettings();
		return resp;

	}

	// ~ Alerts ------------------------------------------------------------
	// ---------------------------------------------------------------------
	private void setupUI(ModelAndView modelView) {
		modelView.addObject("menu", "settings");
	}
}
