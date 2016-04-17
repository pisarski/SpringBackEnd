package com.gmail.sebastian.pisarski.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor which sets variables used on many (all) pages. In most cases this
 * variables are used either on header or footer.
 */
public class CommonVariablesInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Environment env;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		modelAndView.addObject("appRev", env.getProperty("appRev"));
	}

}
