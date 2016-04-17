package com.gmail.sebastian.pisarski.controller;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import com.gmail.sebastian.pisarski.controller.UserController;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.service.UserService;

@RunWith(JUnitParamsRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(userController).build();
	}

	@Test
	public void shouldReturnProperViewWithUserListInModel() throws Exception {
		// given
		List<User> users = new ArrayList<>();
		when(userService.findAll()).thenReturn(users);

		// when
		mockMvc.perform(get("/user/list"))

				// then
				.andExpect(model().attribute("users", users))
				.andExpect(view().name("user.list")).andExpect(status().isOk());
	}

	@Test
	public void shouldDisplayNewUserForm() throws Exception {
		// when
		mockMvc.perform(get("/user/new"))

				// then
				.andExpect(model().attribute("user", isEmptyUser()))
				.andExpect(model().attribute("userLimitExceeded", false))
				.andExpect(model().attribute("submitBtnCode", "button.create"))
				.andExpect(model().attribute("saveActionUrl", "/user/new"))
				.andExpect(view().name("user.new_edit"))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldDisplayEditUserForm() throws Exception {
		// given
		long id = 654L;
		User user = new User();
		user.setId(id);
		when(userService.getById(id)).thenReturn(user);

		// when
		mockMvc.perform(get("/user/edit/" + id))

				// then
				.andExpect(model().attribute("user", user))
				.andExpect(model().attribute("userLimitExceeded", false))
				.andExpect(model().attribute("submitBtnCode", "button.edit"))
				.andExpect(
						model().attribute("saveActionUrl", "/user/edit/" + id))
				.andExpect(view().name("user.new_edit"))
				.andExpect(status().isOk());
	}

	@Test
	@Parameters(method = "getParametersForShouldInsertUserExceededLimitVariable")
	public void shouldInsertUserExceededLimitVariable(long numOfUsers,
			String url, User user) throws Exception {
		// given
		when(userService.getById(anyLong())).thenReturn(user);
		when(userService.getNumberOfUsers()).thenReturn(numOfUsers);

		// when
		mockMvc.perform(get(url))

				// then
				.andExpect(model().attribute("userLimitExceeded", true))
				.andExpect(view().name("user.new_edit"))
				.andExpect(status().isOk());
	}

	@Test
	@Parameters(method = "getParaetersForShouldSaveUser")
	public void shouldSaveUser(String url) throws Exception {
		// given
		String firstName = "firstName";
		String lastName = "lastName";
		String username = "username";
		String password = "password";

		// when
		mockMvc.perform(
				post(url).param("firstName", firstName)
						.param("lastName", lastName)
						.param("username", username)
						.param("password", password))

				// then
				.andExpect(redirectedUrl("/user/list"))
				.andExpect(status().is3xxRedirection());

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userService).save(userCaptor.capture());
		User captured = userCaptor.getValue();
		assertEquals(firstName, captured.getFirstName());
		assertEquals(lastName, captured.getLastName());
		assertEquals(username, captured.getUsername());
		assertEquals(password, captured.getPassword());
	}

	private Matcher<User> isEmptyUser() {
		return new TypeSafeMatcher<User>() {

			@Override
			public void describeTo(Description description) {
				description
						.appendText("new user (all fields with null values)");

			}

			@Override
			protected boolean matchesSafely(User user) {
				return user.getId() == null & user.getFirstName() == null
						&& user.getLastName() == null
						&& user.getUsername() == null
						&& user.getPassword() == null;
			}
		};
	}

	@SuppressWarnings("unused")
	private Object[] getParametersForShouldInsertUserExceededLimitVariable() {
		long id = 654L;
		User user = new User();
		user.setId(id);
		return $($(UserService.MAX_USERS_COUNT, "/user/new", null),
				$(UserService.MAX_USERS_COUNT + 1, "/user/new", null),
				$(UserService.MAX_USERS_COUNT, "/user/edit/43", user),
				$(UserService.MAX_USERS_COUNT + 1, "/user/edit/43", user));
	}

	@SuppressWarnings("unused")
	private String[] getParaetersForShouldSaveUser() {
		return new String[] { "/user/new", "/user/edit/54" };
	}

}
