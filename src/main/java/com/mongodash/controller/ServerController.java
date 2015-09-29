package com.mongodash.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodash.Config.STATUS;
import com.mongodash.model.Breadcrumb;
import com.mongodash.model.Resource;
import com.mongodash.model.Response;
import com.mongodash.model.Server;
import com.mongodash.model.ServerUpdateMethodType;
import com.mongodash.service.ServerService;

@Controller
@RequestMapping("server")
public class ServerController extends BaseCRUDController<Server> {

	@Autowired
	ServerService serverService;

	// ~ Request Mappings ----------------------------------------------------
	// -----------------------------------------------------------------------

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mview = getModelView("list_server", "Mongo Servers", "List of available mongo servers for monitoring");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Administration"));
		bds.add(new Breadcrumb("Servers", "server/list"));
		mview.addObject("breadcrumbs", bds);

		List<Resource> styles = new ArrayList<Resource>();
		styles.add(new Resource("css/table-responsive.css"));
		mview.addObject("styles", styles);

		List<Server> servers = serverService.list();
		mview.addObject("servers", servers);

		setupUI(mview);

		return mview;
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") Integer serverId) {

		Server server = serverService.getById(serverId);
		if (server == null) return getModelView("forward:/404");

		return editView(server);
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView add() {
		return editView(new Server());
	}

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("server") @Valid Server server, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) return editView(server);

		if (server.getId() == null)
			serverService.save(server);
		else
			serverService.update(server);

		redirectAttributes.addFlashAttribute("server", server);
		return new ModelAndView("redirect:/server/list");
	}

	@RequestMapping(value = { "/remove/{id}" }, method = RequestMethod.GET)
	public ModelAndView remove(@PathVariable(value = "id") Integer serverId) {
		serverService.removeById(serverId);
		return new ModelAndView("redirect:/server/list");
	}

	// ~ ajax mappings -------------------------------------------------------
	// -----------------------------------------------------------------------
	@RequestMapping(value = { "/monitor/start/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public Response startMonitor(@PathVariable(value = "id") Integer serverId) {
		return serverService.startMonitor(serverId);
	}

	@RequestMapping(value = { "/monitor/stop/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public Response stopMonitor(@PathVariable(value = "id") Integer serverId) {
		return serverService.stopMonitor(serverId);
	}

	@RequestMapping(value = { "/key" }, method = RequestMethod.GET)
	@ResponseBody
	public Response getServerKey() {
		Response response = new Response();
		response.setStatus(STATUS.OK.name());
		response.setMessage(UUID.randomUUID().toString());
		return response;
	}

	// ~ privates ------------------------------------------------------------
	// -----------------------------------------------------------------------

	private ModelAndView editView(Server server) {

		ModelAndView mview = getModelView("edit_server");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Administration"));
		bds.add(new Breadcrumb("Servers", "server/list"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("server", server);
		mview.addObject("serverUpdateMethodTypes", ServerUpdateMethodType.values());

		if (server.getId() != null && server.getId() > 0) {
			mview.addObject("title", "Edit Mongo Server");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("Edit Mongo Server", "server/edit"));
		} else {
			mview.addObject("title", "New Mongo Server");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("New Mongo Server", "server/new"));
		}

		List<Resource> initJs = new ArrayList<Resource>();
		initJs.add(new Resource("Servers.validateForm();"));
		mview.addObject("inits", initJs);

		setupUI(mview);
		return mview;
	}

	private void setupUI(ModelAndView modelView) {

		modelView.addObject("menu", "administration");
		modelView.addObject("subMenu", "servers");

		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("js/bootstrap-switch.js"));
		modelView.addObject("plugins", pagePlugins);

		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_servers.js"));
		pageScripts.add(new Resource("js/jquery.validate.min.js"));
		modelView.addObject("scripts", pageScripts);

		List<String> modules = new ArrayList<String>();
		modules.add("Servers");
		modelView.addObject("initModules", modules);
	}
}
