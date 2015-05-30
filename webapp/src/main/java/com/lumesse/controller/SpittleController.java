package com.lumesse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lumesse.service.SpittleService;

@Controller
@RequestMapping(value = "/spittle/")
public class SpittleController {

	@Autowired
	private SpittleService spittleService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String listSpittles(Model model) {
		model.addAttribute("spittles", spittleService.findAll());
		return "spittles";
	}
}
