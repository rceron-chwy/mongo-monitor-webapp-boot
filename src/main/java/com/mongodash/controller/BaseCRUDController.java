package com.mongodash.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodash.model.BaseModel;

public abstract class BaseCRUDController<T extends BaseModel> extends BaseController {

	abstract ModelAndView list();

	abstract ModelAndView add();

	abstract ModelAndView edit(Integer id);

	abstract ModelAndView remove(Integer id);

	abstract ModelAndView save(T model, BindingResult result, RedirectAttributes redirectAttributes);

}
