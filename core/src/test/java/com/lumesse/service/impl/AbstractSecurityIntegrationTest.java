package com.lumesse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lumesse.configuration.RootConfiguration;
import com.lumesse.configuration.TestSecurityConfiguration;
import com.lumesse.entity.User;
import com.lumesse.entity.enums.UserRight;
import com.lumesse.pojo.SpittleUserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSecurityConfiguration.class,
		RootConfiguration.class })
@ActiveProfiles("test")
public abstract class AbstractSecurityIntegrationTest {

	@After
	public void clearAuthContext() {
		SecurityContextHolder.clearContext();
	}

	protected void loginWithRights(UserRight... rights) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UserRight right : rights) {
			authorities.add(new SimpleGrantedAuthority(right.name()));
		}
		User user = new User();
		user.setPassword("test");
		user.setUsername("test");
		user.setId(785438L);
		user.setRights(Stream.of(rights).collect(Collectors.toSet()));
		SpittleUserDetails details = new SpittleUserDetails(user);
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(details, null, details
						.getAuthorities()));
	}
}
