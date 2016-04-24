package com.gmail.sebastian.pisarski.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.sebastian.pisarski.dto.spittle.BasicSpittleDto;
import com.gmail.sebastian.pisarski.dto.spittle.SpittleDto;
import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.service.SpittleService;

@Path("spittles")
@Component
public class SpittleRest {

	@Autowired
	private SpittleService spittleService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SpittleDto> list() {
		return spittleService.findAllSorted().stream().map(spittle -> new SpittleDto(spittle))
				.collect(Collectors.toList());
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SpittleDto getSpittleById(@PathParam("id") Long id) {
		return new SpittleDto(spittleService.getById(id));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSpittle(BasicSpittleDto spittleDto, @Context UriInfo uriInfo) {
		Spittle spittle = spittleDto.getEntity();
		Spittle savedSpittle = spittleService.save(spittle);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(savedSpittle.getId()));
		return Response.created(builder.build()).build();
	}

}
