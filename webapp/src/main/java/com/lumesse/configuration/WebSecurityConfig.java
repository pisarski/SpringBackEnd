package com.lumesse.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.lumesse.service.UserAuthService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin")
				.roles("USER", "ADMIN");

		auth.userDetailsService(userAuthService).passwordEncoder(
				passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		setUtf8Encoding(http)
			.exceptionHandling().accessDeniedPage("/404")
		.and().authorizeRequests()
			.antMatchers("/login*").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/spittle/list").permitAll()
		.and().formLogin()
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/login.do")
			.defaultSuccessUrl("/")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
			.failureUrl("/login?error")
		.and().rememberMe()
			.tokenValiditySeconds(2419200)
			.rememberMeParameter("remember-me")
			.key("spittrKey")
		.and().logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
		.and().httpBasic()
			.realmName("Spittr")
		.and().requiresChannel()
			.anyRequest().requiresSecure();
	}

	private HttpSecurity setUtf8Encoding(HttpSecurity http) {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);

		return http;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
