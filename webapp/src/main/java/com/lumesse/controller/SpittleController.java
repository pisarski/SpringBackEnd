package com.lumesse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lumesse.aspect.validation.Validate;
import com.lumesse.entity.Spittle;
import com.lumesse.service.SpittleService;

@Controller
@RequestMapping(value = "spittle")
public class SpittleController {

	@Autowired
	private SpittleService spittleService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String listSpittles(Model model) {
		model.addAttribute("spittles", spittleService.findAllSorted());
		return "spittle.list";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = { "new", "save" }, method = RequestMethod.GET)
	public String addSpittle(Model model) {
		model.addAttribute("spittle", new Spittle());
		return "spittle.new_edit";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Validate("spittle.new_edit")
	public String saveSpittle(@ModelAttribute("spittle") Spittle spittle,
			Errors errors) {

		spittleService.save(spittle);
		return "redirect:list";
	}
}
