package com.lumesse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lumesse.builder.UserBuilder;
import com.lumesse.entity.User;
import com.lumesse.entity.enums.UserRight;
import com.lumesse.service.UserService;

public class UserServiceImplIntegrationTest extends
		AbstractSecurityIntegrationTest {

	@Autowired
	private UserService userService;

	@Test
	public void shouldFetchUserData() {
		// given
		loginWithRights(UserRight.USER_MANAGEMENT);

		User user = new UserBuilder()
				.withAnyValues()
				.withId(null)
				.withRights(
						Stream.of(UserRight.USER_MANAGEMENT).collect(
								Collectors.toSet())).build();

		userService.save(user);

		// when
		List<User> foundUsers = userService.findAll();

		// then
		assertEquals(1, foundUsers.size());
		assertThat(
				user.getRights(),
				Matchers.containsInAnyOrder(foundUsers.get(0).getRights()
						.toArray()));
	}
}
