package com.lumesse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {

	@RequestMapping(value = "/404")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String notFoundError(Model model) {
		model.addAttribute("messageCode", "error.404");
		return "errorCode";
	}

}
