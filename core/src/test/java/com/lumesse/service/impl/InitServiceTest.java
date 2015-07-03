package com.lumesse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lumesse.entity.User;
import com.lumesse.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class InitServiceTest {

	@Mock
	private ApplicationEvent applicationEvent;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder encoder;

	@InjectMocks
	private InitService initService;

	@Before
	public void setUp() {
		mockApplicationContext("Root");
	}

	@Test
	public void shouldDoNothingForNonRootContext() {
		// given
		mockApplicationContext("Non root");

		// when
		initService.onApplicationEvent(applicationEvent);

		// then
		verifyZeroInteractions(userRepository);
	}

	@Test
	public void shouldDoNothingIfAdminExists() {
		// given
		when(userRepository.findByUsername("admin")).thenReturn(new User());

		// when
		initService.onApplicationEvent(applicationEvent);

		// then
		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void shouldCreateAdmin() {
		// given
		String encodedPassword = "encoded";
		when(userRepository.findByUsername("ädmin")).thenReturn(null);
		when(encoder.encode("admin")).thenReturn(encodedPassword);

		// when
		initService.onApplicationEvent(applicationEvent);

		// then
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		ArgumentCaptor<String> passwordCaptor = ArgumentCaptor
				.forClass(String.class);
		verify(userRepository).save(userCaptor.capture());
		verify(encoder).encode(passwordCaptor.capture());
		User admin = userCaptor.getValue();

		assertEquals("admin", admin.getUsername());
		assertEquals(encodedPassword, admin.getPassword());
		assertEquals("admin", passwordCaptor.getValue());
	}

	private void mockApplicationContext(String contextDisplayName) {
		AbstractApplicationContext context = mock(AbstractApplicationContext.class);
		when(context.getDisplayName()).thenReturn(contextDisplayName);
		when(applicationEvent.getSource()).thenReturn(context);
	}
}
