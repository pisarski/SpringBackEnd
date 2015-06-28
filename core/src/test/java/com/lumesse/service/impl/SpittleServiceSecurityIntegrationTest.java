package com.lumesse.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.lumesse.entity.Spittle;
import com.lumesse.entity.enums.UserRight;
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
		loginWithRights(UserRight.USER_MANAGEMENT);

		// then
		expected.expect(AccessDeniedException.class);

		// when
		spittleService.save(new Spittle());
	}

	@Test
	public void shouldInvokeSaveForProperRight() {
		// given
		loginWithRights(UserRight.ADD_SPITTLE);

		// then
		expected.expect(ValidationException.class);

		// when
		spittleService.save(new Spittle());
	}
}
