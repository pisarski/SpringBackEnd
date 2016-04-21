package com.gmail.sebastian.pisarski.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.service.SpittleService;

@Component
@Path("/test")
public class Test {

	@Autowired
	private SpittleService service;

	@GET
	@Produces("application/json")
	@Path("/test")
	public Spittle test() {
		return new Spittle();
	}
}