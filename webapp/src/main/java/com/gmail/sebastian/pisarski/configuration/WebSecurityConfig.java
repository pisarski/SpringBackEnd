package com.gmail.sebastian.pisarski.configuration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.gmail.sebastian.pisarski.service.UserAuthService;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		auth.userDetailsService(userAuthService).passwordEncoder(
				passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/api-doc/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		setUtf8Encoding(http)
			.csrf()
				.ignoringAntMatchers("/rest/**")
			.and().exceptionHandling()
				.accessDeniedPage("/404")
			.and().requestMatcher(new OAuthRequestedMatcher()).authorizeRequests()
				.antMatchers("/login*").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/spittle/list").permitAll()
				.antMatchers("/rest/swagger.json").permitAll()
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
				.logoutUrl("/logout").permitAll()
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
	
	private static class OAuthRequestedMatcher implements RequestMatcher {
	    @Override
	    public boolean matches(HttpServletRequest request) {
	    	boolean hasAccessTokenParam = StringUtils.isNotEmpty(request.getParameter("access_token"));
	        String auth = request.getHeader("Authorization");
	        // Determine if the client request contained an OAuth Authorization
	        return !hasAccessTokenParam && ((auth == null) || !auth.startsWith("Bearer"));
	    }
	}
}
