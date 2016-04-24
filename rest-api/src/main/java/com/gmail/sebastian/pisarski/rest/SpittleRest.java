package com.gmail.sebastian.pisarski.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.sebastian.pisarski.dto.SpittleDto;
import com.gmail.sebastian.pisarski.service.SpittleService;

@Path("spittles")
@Component
public class SpittleRest {

	@Autowired
	private SpittleService spittleService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SpittleDto> list() {
		return spittleService
				.findAllSorted()
				.stream().map(spittle -> new SpittleDto(spittle))
				.collect(Collectors.toList());
	}
}
