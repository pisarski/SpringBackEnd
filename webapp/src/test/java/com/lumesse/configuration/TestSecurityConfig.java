package com.lumesse.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lumesse.service.UserAuthService;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig extends WebSecurityConfig {

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin")
				.roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.requiresChannel().anyRequest().requiresInsecure();
	}

	@Bean
	public UserAuthService userService() {
		return Mockito.mock(UserAuthService.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return Mockito.mock(PasswordEncoder.class);
	}

}
