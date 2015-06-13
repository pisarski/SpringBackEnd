package com.lumesse.configuration;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.lumesse.interceptor.CommonVariablesInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan("com.lumesse.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@PostConstruct
	public void init() {
		requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions("/WEB-INF/layout/tiles.xml");
		tiles.setCheckRefresh(env.getProperty("tiles.refresh", Boolean.class,
				false));
		return tiles;
	}

	@Bean
	public ViewResolver viewResolver() {
		return new TilesViewResolver();
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/translations/messages");
		messageSource.setCacheSeconds(10);
		return messageSource;
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public CommonVariablesInterceptor commonVariablesInterceptor() {
		return new CommonVariablesInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(commonVariablesInterceptor());
	}

	/**
	 * Used instead of
	 * 
	 * @Bean public ViewResolver viewResolver() { return new
	 *       TilesViewResolver(); }
	 * 
	 *       to hide model attributes in case of redirection
	 */
	@Bean
	public ViewResolver redirectsNotExposingModelUrlBasedViewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver() {

			@Override
			protected View createView(String viewName, Locale locale)
					throws Exception {
				View view = super.createView(viewName, locale);
				if (view instanceof RedirectView) {
					((RedirectView) view).setExposeModelAttributes(false);
				}
				return view;
			}
		};

		viewResolver.setViewClass(TilesView.class);
		return viewResolver;
	}

}
