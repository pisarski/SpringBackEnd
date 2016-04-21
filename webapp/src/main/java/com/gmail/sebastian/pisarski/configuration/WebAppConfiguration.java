package com.gmail.sebastian.pisarski.configuration;

import javax.servlet.ServletContext;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.gmail.sebastian.pisarski.web.WebConfig;

public class WebAppConfiguration extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		// set in RestAppConfiguration
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
