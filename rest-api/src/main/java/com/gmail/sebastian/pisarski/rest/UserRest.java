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

import com.gmail.sebastian.pisarski.dto.user.BasicUserDto;
import com.gmail.sebastian.pisarski.dto.user.UserDto;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.service.UserService;

@Api("/users")
@Path("users")
@Component
public class UserRest {

	@Autowired
	private UserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Returns list of all users")
	public List<UserDto> getUsers() {
		return userService.findAll().stream().map(u -> new UserDto(u))
				.collect(Collectors.toList());
	}

	@Path("/me")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Returns logged user")
	public UserDto getLoggedUser() {
		return new UserDto(userService.getLoggedUser());
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Returns user with given id")
	@ApiResponses({
			@ApiResponse(code = 200, message = "User was found and returned"),
			@ApiResponse(code = 404, message = "User not found") })
	public UserDto getById(
			@PathParam("id") @ApiParam(value = "id of the user", required = true) Long id) {
		User user = userService.getById(id);
		if (user == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return new UserDto(user);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation("Creates new user")
	@ApiResponses({ @ApiResponse(code = 204, message = "User was created"),
			@ApiResponse(code = 400, message = "Validation failed") })
	public Response addUser(
			@ApiParam(value = "User to be added", required = true) BasicUserDto userDto,
			@Context UriInfo uriInfo) {
		User user = userDto.getEntity();
		User savedUser = userService.save(user);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(savedUser.getId()));
		return Response.created(builder.build()).build();
	}

	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation("Edits user")
	@ApiResponses({
			@ApiResponse(code = 204, message = "User was successfully updated"),
			@ApiResponse(code = 400, message = "Validation failed") })
	public Response editSpittle(
			@PathParam("id") @ApiParam(value = "Id of user to be edited", required = true) Long id,
			@ApiParam(value = "User data", required = true) BasicUserDto userDto) {
		User user = userDto.getEntity();
		user.setId(id);
		userService.save(user);
		return Response.noContent().build();
	}
}
