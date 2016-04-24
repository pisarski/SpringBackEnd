package com.gmail.sebastian.pisarski.configuration;

import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;

public class RestListAutoConfiguration extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Reflections reflections = new Reflections("com.gmail.sebastian.pisarski.rest");
		return reflections.getTypesAnnotatedWith(Path.class);
	}

}
