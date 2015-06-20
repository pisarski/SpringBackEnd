package com.lumesse.controller;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lumesse.editors.DateEditor;

@ControllerAdvice
public class BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView commonExceptionHandler(Throwable exception) {
		UUID uuid = UUID.randomUUID();
		logger.error("Exception occured: " + uuid, exception);
		ModelAndView model = new ModelAndView("exception");
		model.addObject("uuid", uuid.toString());
		return model;
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String accessDeniedExceptionHandler() {
		return "forward:/404";
	}

}
