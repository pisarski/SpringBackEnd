package com.lumesse.controller;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

import com.lumesse.entity.User;
import com.lumesse.helpers.ValidationHelper;
import com.lumesse.service.UserService;

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
	@Parameters(method = "getParametersForShouldDisplayNewUserForm")
	public void shouldDisplayNewUserForm(String context) throws Exception {
		// when
		mockMvc.perform(get("/user/" + context))

				// then
				.andExpect(model().attribute("user", isEmptyUser()))
				.andExpect(model().attribute("userLimitExceeded", false))
				.andExpect(view().name("user.new_edit"))
				.andExpect(status().isOk());
	}

	@Test
	@Parameters(method = "getParametersForShouldInsertUserExceededLimitVariable")
	public void shouldInsertUserExceededLimitVariable(String context,
			long numOfUsers) throws Exception {
		// given
		when(userService.getNumberOfUsers()).thenReturn(numOfUsers);

		// when
		mockMvc.perform(get("/user/" + context))

				// then
				.andExpect(model().attribute("user", isEmptyUser()))
				.andExpect(model().attribute("userLimitExceeded", true))
				.andExpect(view().name("user.new_edit"))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldSaveNewUser() throws Exception {
		// given
		String firstName = "firstName";
		String lastName = "lastName";
		String username = "username";
		String password = "password";

		// when
		mockMvc.perform(
				post("/user/save").param("firstName", firstName)
						.param("lastName", lastName)
						.param("username", username)
						.param("password", password))

				// then
				.andExpect(redirectedUrl("list"))
				.andExpect(status().is3xxRedirection());

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userService).save(userCaptor.capture());
		User captured = userCaptor.getValue();
		assertEquals(firstName, captured.getFirstName());
		assertEquals(lastName, captured.getLastName());
		assertEquals(username, captured.getUsername());
		assertEquals(password, captured.getPassword());
	}

	@Test
	public void shouldGoBackToFormInCaseOfValidationErrorsWhenNewSpittleIsCreated()
			throws Exception {
		// given
		String field = "firstName";
		String msg = "testErrorMsg";
		mockMvc = standaloneSetup(userController).setValidator(
				ValidationHelper.alwaysFailValidator(field, msg)).build();

		// when
		mockMvc.perform(post("/user/save"))

				// then
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(view().name("user.new_edit"));
	}

	@Test
	@Parameters(method = "getParametersForShouldSaveUserIfUsernameIsUnique")
	public void shouldSaveUserIfUsernameIsUnique(String userId,
			User existingUser) throws Exception {
		// given
		when(userService.findByUsername(anyString())).thenReturn(existingUser);

		// when
		mockMvc.perform(post("/user/save").param("id", userId))

				// then
				.andExpect(view().name("redirect:list"))
				.andExpect(status().is3xxRedirection());

		verify(userService).save(any(User.class));
	}

	@Test
	@Parameters(method = "getParametersForShouldDetectThatUsernameIsUsed")
	public void shouldDetectThatUsernameIsUsed(String userId, User existingUser)
			throws Exception {

		// given
		when(userService.findByUsername(anyString())).thenReturn(existingUser);

		// when
		mockMvc.perform(post("/user/save").param("id", userId))

				// then
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(view().name("user.new_edit"))
				.andExpect(status().isOk());

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
	private String[] getParametersForShouldDisplayNewUserForm() {
		return new String[] { "new", "save" };
	}

	@SuppressWarnings("unused")
	private Object[] getParametersForShouldInsertUserExceededLimitVariable() {
		return $($("new", UserService.MAX_USERS_COUNT),
				$("save", UserService.MAX_USERS_COUNT),
				$("new", UserService.MAX_USERS_COUNT + 1),
				$("save", UserService.MAX_USERS_COUNT + 1));
	}

	@SuppressWarnings("unused")
	private Object[] getParametersForShouldSaveUserIfUsernameIsUnique() {
		User userWithId = new User();
		userWithId.setId(1L);
		return $($(null, null), $(userWithId.getId().toString(), userWithId));
	}

	@SuppressWarnings("unused")
	private Object[] getParametersForShouldDetectThatUsernameIsUsed() {
		User userWithId = new User();
		userWithId.setId(1L);
		return $($(null, userWithId), $("5", userWithId));
	}

}
