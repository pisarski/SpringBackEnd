package com.gmail.sebastian.pisarski.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.sebastian.pisarski.entity.Spittle;

@RestController
public class TestRest {

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Spittle test() {
		return new Spittle();
	}
}
