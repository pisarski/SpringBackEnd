package com.lumesse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lumesse.configuration.RootConfiguration;
import com.lumesse.configuration.TestSecurityConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSecurityConfiguration.class,
		RootConfiguration.class })
@ActiveProfiles("test")
public abstract class AbstractSecurityIntegrationTest {

	@After
	public void clearAuthContext() {
		SecurityContextHolder.clearContext();
	}

	protected void loginWithRoles(String... roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		User principal = new User("test", "test", authorities);
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(principal, null,
						principal.getAuthorities()));
	}
}
