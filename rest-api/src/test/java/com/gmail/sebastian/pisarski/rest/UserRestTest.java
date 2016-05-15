package com.gmail.sebastian.pisarski.rest;

import static com.gmail.sebastian.pisarski.rest.assertions.HeaderMatcher.containsHeaderWithValue;
import static com.gmail.sebastian.pisarski.rest.assertions.StatusMatcher.hasStatus;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.jboss.resteasy.mock.MockHttpRequest.get;
import static org.jboss.resteasy.mock.MockHttpRequest.post;
import static org.jboss.resteasy.mock.MockHttpRequest.put;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gmail.sebastian.pisarski.builder.UserBuilder;
import com.gmail.sebastian.pisarski.dto.user.BasicUserDto;
import com.gmail.sebastian.pisarski.dto.user.UserDto;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserRestTest extends BaseRestTest {

	@InjectMocks
	private static UserRest userRest;

	@Mock
	private UserService userService;

	@Test
	public void shouldGetUsers() throws Exception {
		// given
		User user = new UserBuilder().withAnyValues().build();
		when(userService.findAll()).thenReturn(asList(user));

		MockHttpRequest request = get("/users");

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.OK));
		assertThat(output, equalTo(jsonOf(asList(new UserDto(user)))));
	}

	@Test
	public void shouldGetLoggedUser() throws Exception {
		// given
		MockHttpRequest request = MockHttpRequest.get("/users/me");

		User user = new UserBuilder().withAnyValues().build();
		when(userService.getLoggedUser()).thenReturn(user);

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.OK));
		assertThat(output, equalTo(jsonOf(new UserDto(user))));
	}

	@Test
	public void shouldGetUser() throws Exception {
		// given
		User user = new UserBuilder().withAnyValues().build();
		when(userService.getById(user.getId())).thenReturn(user);

		MockHttpRequest request = get("/users/" + user.getId());

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.OK));
		assertThat(output, equalTo(jsonOf(new UserDto(user))));
	}

	@Test
	public void shouldNotFindUser() throws Exception {
		// given
		MockHttpRequest request = get("/users/-1");

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.NOT_FOUND));
		assertThat(output, equalTo(""));
	}

	@Test
	public void shouldAddUser() throws Exception {
		// given
		MockHttpRequest request = post("/users").content(jsonOf(new BasicUserDto()).getBytes())
				.contentType(MediaType.APPLICATION_JSON);

		User savedUser = new UserBuilder().withAnyValues().build();
		when(userService.save(any(User.class))).thenReturn(savedUser);

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.CREATED));
		assertThat(response, containsHeaderWithValue("Location", "/users/" + savedUser.getId()));
		assertThat(output, equalTo(""));
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		// given
		User user = new UserBuilder().withAnyValues().build();
		Long idOfUserToUpdate = 9824L;
		MockHttpRequest request = put("/users/" + idOfUserToUpdate).content(jsonOf(new BasicUserDto(user)).getBytes())
				.contentType(MediaType.APPLICATION_JSON);

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.NO_CONTENT));
		assertThat(output, equalTo(""));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userService).save(userCaptor.capture());
		User updatedUser = userCaptor.getValue();

		assertEquals(idOfUserToUpdate, updatedUser.getId());
		assertEquals(user.getFirstName(), updatedUser.getFirstName());
		assertEquals(user.getLastName(), updatedUser.getLastName());
		assertEquals(user.getRights(), updatedUser.getRights());
	}

	@Override
	protected void setUp() {
	}

	@Override
	protected Object getRestService() {
		return userRest;
	}
}
