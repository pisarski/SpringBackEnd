package com.lumesse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lumesse.repository.SpittleRepository;

@Controller
public class HomeController {

	@Autowired
	private SpittleRepository spittleRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
}
