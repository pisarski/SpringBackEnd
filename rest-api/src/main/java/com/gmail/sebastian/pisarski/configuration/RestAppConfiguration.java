package com.gmail.sebastian.pisarski.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.gmail.sebastian.pisarski.context.listener.MyContextLoaderListener;
import com.gmail.sebastian.pisarski.rest.Test;

public class RestAppConfiguration extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected void registerDispatcherServlet(ServletContext servletContext) {
		String servletName = "resteasy-servlet";

		HttpServletDispatcher servletDispatcher = new HttpServletDispatcher();
		ServletRegistration.Dynamic registration = servletContext.addServlet(
				servletName, servletDispatcher);

		registration.addMapping(getServletMappings());

		servletContext.setInitParameter("resteasy.servlet.mapping.prefix",
				"/rest");
		servletContext.setInitParameter("resteasy.resources",
				Test.class.getName());
		servletContext.setInitParameter("contextClass",
				AnnotationConfigWebApplicationContext.class.getName());
		servletContext.setInitParameter("contextConfigLocation",
				RootConfiguration.class.getName());
	}

	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(ResteasyBootstrap.class);
		servletContext.addListener(MyContextLoaderListener.class);
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/rest/*" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
}
