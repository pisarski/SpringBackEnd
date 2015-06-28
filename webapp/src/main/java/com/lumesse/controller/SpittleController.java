package com.lumesse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PreAuthorize("hasAuthority('ADD_SPITTLE')")
	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String addSpittle(Model model) {
		initSpittleFormModel(model, new Spittle());
		return "spittle.new_edit";
	}

	@PreAuthorize("hasAnyAuthority('EDIT_ALL_SPITTLES', 'EDIT_OWN_SPITTLE')")
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String editSpittle(Model model, @PathVariable("id") long id) {
		initSpittleFormModel(model, spittleService.getById(id));
		return "spittle.new_edit";
	}

	@PreAuthorize("hasAuthority('ADD_SPITTLE')")
	@RequestMapping(value = "new", method = RequestMethod.POST)
	@Validate(value = "spittle.new_edit", initMethod = "initSpittleFormModel")
	public String saveNewSpittle(@ModelAttribute("spittle") Spittle spittle,
			Model model, Errors errors) {

		spittleService.save(spittle);
		return "redirect:/spittle/list";
	}

	@PreAuthorize("hasAnyAuthority('EDIT_ALL_SPITTLES', 'EDIT_OWN_SPITTLE')")
	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	@Validate(value = "spittle.new_edit", initMethod = "initSpittleFormModel")
	public String updateSpittle(@ModelAttribute("spittle") Spittle spittle,
			Model model, Errors errors) {

		spittleService.save(spittle);
		return "redirect:/spittle/list";
	}

	private void initSpittleFormModel(Model model, Spittle spittle) {
		model.addAttribute("spittle", spittle);
		if (spittle.getId() == null) {
			model.addAttribute("submitBtnCode", "button.create");
			model.addAttribute("saveActionUrl", "new");
		} else {
			model.addAttribute("submitBtnCode", "button.edit");
			model.addAttribute("saveActionUrl", "edit/" + spittle.getId());
		}
	}
}
