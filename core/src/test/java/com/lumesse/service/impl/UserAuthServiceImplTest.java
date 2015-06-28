package com.lumesse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lumesse.builder.UserBuilder;
import com.lumesse.entity.User;
import com.lumesse.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserAuthServiceImpl userAuthService;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldThrowExceptionIfUserNotFound() {
		// given
		String username = "nonExistingUsername";

		// then
		expected.expect(UsernameNotFoundException.class);
		expected.expectMessage("Cannot find user with username: " + username);

		// when
		userAuthService.loadUserByUsername(username);
	}

	@Test
	public void shouldLoadExistingUserDetails() {
		// given
		String username = "username";
		User user = new UserBuilder().withAnyValues().withUsername(username)
				.build();
		when(userRepository.findByUsername(username)).thenReturn(user);

		// when
		UserDetails userDetails = userAuthService.loadUserByUsername(username);

		// then
		assertEquals(user.getUsername(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
		assertEquals(user.isEnabled(), userDetails.isAccountNonExpired());
		assertEquals(user.isEnabled(), userDetails.isAccountNonLocked());
		assertEquals(user.isEnabled(), userDetails.isCredentialsNonExpired());
		assertEquals(user.isEnabled(), userDetails.isEnabled());
		assertThat(
				userDetails.getAuthorities().stream()
						.map(auth -> auth.getAuthority())
						.collect(Collectors.toSet()),
				Matchers.containsInAnyOrder(user.getRights().stream()
						.map(role -> role.name()).toArray()));
	}

}
