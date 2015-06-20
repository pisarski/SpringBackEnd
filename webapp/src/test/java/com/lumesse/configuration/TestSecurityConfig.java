package com.lumesse.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig extends WebSecurityConfig {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.requiresChannel().anyRequest().requiresInsecure();
	}

}
