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

import com.mongodash.model.Breadcrumb;
import com.mongodash.model.Resource;
import com.mongodash.model.User;
import com.mongodash.model.UserRole;
import com.mongodash.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends BaseCRUDController<User> {

	@Autowired
	UserService userService;

	// ~ Request Mappings ----------------------------------------------------
	// -----------------------------------------------------------------------

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mview = getModelView("list_user", "Application Users", "List of users able to use this application");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Administration", "administration"));
		bds.add(new Breadcrumb("Users", "user/list"));
		mview.addObject("breadcrumbs", bds);

		List<Resource> styles = new ArrayList<Resource>();
		styles.add(new Resource("css/table-responsive.css"));
		mview.addObject("styles", styles);

		List<User> users = userService.list();
		mview.addObject("users", users);

		setupUI(mview);

		return mview;
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView add() {
		return editView(new User());
	}
	
	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") Integer userId) {

		User user = userService.getById(userId);
		if (user == null) return getModelView("forward:/404");
		
		return editView(user);
	}
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("user") @Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) return editView(user);

		if (user.getId() == null)
			userService.save(user);
		else
			userService.update(user);

		redirectAttributes.addFlashAttribute("user", user);
		return new ModelAndView("redirect:/user/list");
	}

	@RequestMapping(value = { "/remove/{id}" }, method = RequestMethod.GET)
	public ModelAndView remove(@PathVariable(value = "id") Integer userId) {
		userService.removeById(userId);
		return new ModelAndView("redirect:/user/list");
	}

	// ~ private to controller -----------------------------------------------
	// -----------------------------------------------------------------------

	private ModelAndView editView(User user) {

		ModelAndView mview = getModelView("edit_user");

		List<Breadcrumb> bds = new ArrayList<Breadcrumb>();
		bds.add(new Breadcrumb("Administration", "administration"));
		bds.add(new Breadcrumb("Users", "user/list"));
		mview.addObject("breadcrumbs", bds);
		mview.addObject("user", user);
		mview.addObject("userRoles", UserRole.values());
		
		if (user.getId() != null && user.getId() > 0) {
			mview.addObject("title", "New Application User");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("New Application User", "user/new"));
		} else {
			mview.addObject("title", "Edit Application User");
			mview.addObject("description", "test");
			bds.add(new Breadcrumb("Edit Application User", "user/edit"));
		}

		List<Resource> initJs = new ArrayList<Resource>();
		initJs.add(new Resource("Servers.validateForm();"));
		mview.addObject("inits", initJs);

		setupUI(mview);
		return mview;
	}

	private void setupUI(ModelAndView modelView) {

		modelView.addObject("menu", "administration");
		modelView.addObject("subMenu", "users");

		List<Resource> pagePlugins = new ArrayList<Resource>();
		pagePlugins.add(new Resource("js/bootstrap-switch.js"));
		modelView.addObject("plugins", pagePlugins);

		List<Resource> pageScripts = new ArrayList<Resource>();
		pageScripts.add(new Resource("js/app_servers.js"));
		pageScripts.add(new Resource("js/jquery.validate.min.js"));
		modelView.addObject("scripts", pageScripts);
	}
}
