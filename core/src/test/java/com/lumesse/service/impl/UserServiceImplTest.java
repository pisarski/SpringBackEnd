package com.lumesse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lumesse.builder.UserBuilder;
import com.lumesse.entity.User;
import com.lumesse.exception.CustomError;
import com.lumesse.exception.ValidationException;
import com.lumesse.repository.UserRepository;
import com.lumesse.service.UserService;

@RunWith(JUnitParamsRunner.class)
public class UserServiceImplTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldDelegateInvocationOfFindAll() {
		// given
		List<User> users = new ArrayList<>();
		when(userRepository.findAll()).thenReturn(users);

		// when
		List<User> result = userService.findAll();

		// then
		assertTrue(result == users);
	}

	@Test
	public void shouldDelegateInvocationOfGetNumberOfUsers() {
		// given
		long numOfUsers = 5432L;
		when(userRepository.count()).thenReturn(numOfUsers);

		// when
		long result = userService.getNumberOfUsers();

		// then
		assertEquals(numOfUsers, result);
	}

	@Test
	public void shouldDelegateInvocationOfFindByUsername() {
		// given
		String username = "username";
		User user = new User();
		when(userRepository.findByUsername(username)).thenReturn(user);

		// when
		User result = userService.findByUsername(username);

		// then
		assertTrue(result == user);
	}

	@Test
	@Parameters(method = "getParametersForShouldThrowExceptionIfUserLimitExceeded")
	public void shouldThrowExceptionIfUserLimitExceeded(long numberOfUsers) {
		// given
		when(userRepository.count()).thenReturn(numberOfUsers);

		// when
		try {
			userService.save(new User());
			fail("ValidationException was expected");

			// then
		} catch (ValidationException e) {
			CustomError validationError = getValidationError(e,
					"user.error.maxNumExceeded", null,
					UserService.MAX_USERS_COUNT);
			assertNotNull(validationError);
		}

	}

	@Test
	public void shouldEncodePassword() {
		// given
		String rawPassword = "password";
		String encodedPassword = "encoded";

		User user = new User();
		user.setPassword(rawPassword);

		when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
		doAnswer(invocation -> invocation.getArgumentAt(0, User.class)).when(
				userRepository).save(any(User.class));

		// when
		User saved = userService.save(user);

		// then
		assertEquals(encodedPassword, saved.getPassword());
	}

	@Test
	public void shouldThrowExceptionOnPasswordChange() {
		// given
		String currentPassword = "currentPassword";
		String newPassword = "newPassword";
		Long userId = 54L;

		User existingUser = new User();
		existingUser.setId(userId);
		existingUser.setPassword(currentPassword);

		User user = new User();
		user.setId(userId);
		user.setPassword(newPassword);

		when(userRepository.findOne(userId)).thenReturn(existingUser);

		// then
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage("password cannot be changed");

		// when
		userService.save(user);
	}

	@Test
	public void shouldEditExistingUser() {
		// given
		Long userId = 54L;
		String newFirstName = "edited";

		User existingUser = createUser("test");
		existingUser.setId(userId);

		User user = createUser("test");
		user.setId(userId);
		user.setFirstName(newFirstName);

		when(userRepository.findOne(userId)).thenReturn(existingUser);
		doAnswer(invocation -> invocation.getArgumentAt(0, User.class)).when(
				userRepository).save(any(User.class));

		// when
		User result = userService.save(user);

		// then
		assertEquals(newFirstName, result.getFirstName());
	}

	@Test
	public void shouldCheckIfUsernameIsTaken() {
		// given
		User user = new User();
		user.setId(54L);

		when(userRepository.findByUsername(anyString())).thenReturn(user);
		// when
		try {
			userService.save(new User());
			fail("ValidationException was expected");
		} catch (ValidationException e) {
			// then
			CustomError validationError = getValidationError(e,
					"user.username.Unique", "username", null);
			assertNotNull(validationError);
		}
	}

	private User createUser(String username) {
		return new UserBuilder().withAnyValues().withUsername(username).build();
	}

	@SuppressWarnings("unused")
	private Long[] getParametersForShouldThrowExceptionIfUserLimitExceeded() {
		return new Long[] { UserService.MAX_USERS_COUNT,
				UserService.MAX_USERS_COUNT + 1 };
	}

	private CustomError getValidationError(ValidationException e,
			final String errorCode, final String field, final Object msgValue) {
		return e.getCustomErrors()
				.stream()
				.filter(err -> err.getErrorCode().equals(errorCode)
						&& err.getField() == field
						&& (msgValue == null || err.getMessageVariables()[0]
								.equals(msgValue)))
				.collect(Collectors.toList()).get(0);
	}
}
