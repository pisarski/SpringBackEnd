package com.lumesse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	@ExceptionHandler(value = Throwable.class)
	public void exception(Throwable exception) {
		logger.error("Exception occured", exception);
	}
}
