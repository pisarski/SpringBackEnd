package com.lumesse.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.lumesse.entity.Spittle;
import com.lumesse.exception.ValidationException;
import com.lumesse.service.SpittleService;

public class SpittleServiceSecurityIntegrationTest extends
		AbstractSecurityIntegrationTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Autowired
	private SpittleService spittleService;

	@Test
	public void shouldThrowAccessDeniedExceptionForNonAuthUser() {
		// given
		loginWithRoles("ROLE_TEST");

		// then
		expected.expect(AccessDeniedException.class);

		// when
		spittleService.save(null);
	}

	@Test
	public void shouldInvokeSaveForRoleUser() {
		// given
		loginWithRoles("ROLE_USER");

		// then
		expected.expect(ValidationException.class);

		// when
		spittleService.save(new Spittle());
	}
}
