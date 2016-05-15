package com.gmail.sebastian.pisarski.service.impl;

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

import com.gmail.sebastian.pisarski.configuration.DevDbConfig;
import com.gmail.sebastian.pisarski.configuration.DomainConfiguration;
import com.gmail.sebastian.pisarski.configuration.TestSecurityConfiguration;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;
import com.gmail.sebastian.pisarski.pojo.SpittleUserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSecurityConfiguration.class, DomainConfiguration.class, DevDbConfig.class })
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
		loginAsUser(user);
	}

	protected void loginAsUser(User user) {
		SpittleUserDetails details = new SpittleUserDetails(user);
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities()));
	}

	protected void runWithParams(Run run, Object[] params) {
		runWithParamsAndExpectedException(run, params, null);
	}

	protected void runWithParamsAndExpectedException(Run run, Object[] params, Class<? extends Throwable> expected) {
		for (Object param : params) {
			try {
				System.out.println("Run with param: " + param);
				run.run(param);
				if (expected != null) {
					throw new RuntimeException("Exception " + expected.getName() + "was expected");
				}
			} catch (Throwable e) {
				if (expected == null || !expected.isInstance(e)) {
					throw new RuntimeException("Failed for param " + param, e);
				}
			}
		}
	}

	public interface Run {
		void run(Object param);
	}
}
