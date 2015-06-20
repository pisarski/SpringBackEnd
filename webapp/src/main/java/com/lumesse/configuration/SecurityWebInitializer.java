package com.lumesse.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Initializes DelegatingFilterProxy which will delagete all requests to filter
 * with ID springSecurityFilterChain
 */
public class SecurityWebInitializer extends
		AbstractSecurityWebApplicationInitializer {

}
