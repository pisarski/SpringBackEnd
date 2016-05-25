package com.gmail.sebastian.pisarski.configuration;

import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;

import io.swagger.jaxrs.config.BeanConfig;

public class RestListAutoConfiguration extends Application {

	public RestListAutoConfiguration() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setTitle("Spittr REST API Documentation");
		beanConfig.setContact("Sebastian Pisarski (pisarski.sebastian@gmail.com)");
        beanConfig.setVersion("1.0.0");
        beanConfig.setBasePath("/SpringSampleApp/rest");
        beanConfig.setResourcePackage("com.gmail.sebastian.pisarski.rest");
        beanConfig.setScan(true);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		Reflections reflections = new Reflections("com.gmail.sebastian.pisarski.rest");
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Path.class);
	    
		classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return classes;
	}

}
