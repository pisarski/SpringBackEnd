package com.lumesse.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages = "com.lumesse.configuration")
@PropertySources({
		@PropertySource(value = "classpath:/properties/default.properties"),
		@PropertySource(value = "file:${APP_CONFIG_DIR}/settings.properties", ignoreResourceNotFound = true),
		@PropertySource(value = "classpath:/properties/commonVariables.properties") })
public class RootConfiguration {

}
