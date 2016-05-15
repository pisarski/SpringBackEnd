package com.gmail.sebastian.pisarski.rest;

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

@Path("users")
@Component
public class UserRest {

	@Autowired
	private UserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDto> getUsers() {
		return userService.findAll().stream().map(u -> new UserDto(u)).collect(Collectors.toList());
	}

	@Path("/me")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto getLoggedUser() {
		return new UserDto(userService.getLoggedUser());
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserDto getById(@PathParam("id") Long id) {
		User user = userService.getById(id);
		if (user == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return new UserDto(user);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(BasicUserDto userDto, @Context UriInfo uriInfo) {
		User user = userDto.getEntity();
		User savedUser = userService.save(user);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(savedUser.getId()));
		return Response.created(builder.build()).build();
	}

	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editSpittle(@PathParam("id") Long id, BasicUserDto userDto) {
		User user = userDto.getEntity();
		user.setId(id);
		userService.save(user);
		return Response.noContent().build();
	}
}
