package com.gmail.sebastian.pisarski.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gmail.sebastian.pisarski.entity.enums.UserRight;
import com.gmail.sebastian.pisarski.service.UserAuthService;

public class UserAuthServiceSecurityIntegrationTest extends AbstractSecurityIntegrationTest {

	@Autowired
	private UserAuthService userAuthService;

	@Test
	public void notAuthenticatedUserShouldInvokeLoadUserByUsername() {
		userAuthService.loadUserByUsername("");
	}

	@Test
	public void allUsersShouldInvokeLoadUserByUsername() {
		runWithParams(right -> {
			// given
			loginWithRights((UserRight) right);

			// when
			userAuthService.loadUserByUsername("");
		}, UserRight.values());
	}
}
