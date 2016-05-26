package com.gmail.sebastian.pisarski.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.sebastian.pisarski.dto.spittle.BasicSpittleDto;
import com.gmail.sebastian.pisarski.dto.spittle.SpittleDto;
import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.service.SpittleService;

@Api(value = "/spittles")
@Path("spittles")
@Component
public class SpittleRest {

	@Autowired
	private SpittleService spittleService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Lists all spittles", notes = "Results are sorted, so that newest spittles are first")
	public List<SpittleDto> list() {
		return spittleService.findAllSorted().stream()
				.map(spittle -> new SpittleDto(spittle))
				.collect(Collectors.toList());
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Returns spittle with given id")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Spittle was found", response = SpittleDto.class),
			@ApiResponse(code = 404, message = "Spittle with given id was not found") })
	public SpittleDto getSpittleById(
			@PathParam("id") @ApiParam(value = "id of the spittle", required = true) Long id) {
		Spittle spittle = spittleService.getById(id);
		if (spittle == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return new SpittleDto(spittle);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Adds new spittle", notes = "Current user will be set as create user")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Spittle was successfully created"),
			@ApiResponse(code = 400, message = "Validation failed") })
	public Response addSpittle(
			@ApiParam(value = "Spittle to be added", required = true) BasicSpittleDto spittleDto,
			@Context UriInfo uriInfo) {
		Spittle spittle = spittleDto.getEntity();
		Spittle savedSpittle = spittleService.save(spittle);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(savedSpittle.getId()));
		return Response.created(builder.build()).build();
	}

	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Edits spittle", notes = "Current user will be set as update user")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Spittle was successfully updated"),
			@ApiResponse(code = 400, message = "Validation failed") })
	public Response editSpittle(
			@PathParam("id") @ApiParam(value = "Id of spittle to be edited", required = true) Long id,
			@ApiParam(value = "Spittle data", required = true) BasicSpittleDto spittleDto) {
		Spittle spittle = spittleDto.getEntity();
		spittle.setId(id);
		spittleService.save(spittle);
		return Response.noContent().build();
	}
}
