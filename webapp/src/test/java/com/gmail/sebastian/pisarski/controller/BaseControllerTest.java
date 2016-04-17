package com.gmail.sebastian.pisarski.controller;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.gmail.sebastian.pisarski.controller.BaseController;
import com.gmail.sebastian.pisarski.controller.SpittleController;

@RunWith(MockitoJUnitRunner.class)
public class BaseControllerTest {

	private static final String UUID_PATTERN = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";

	@Mock
	private SpittleController controller;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = standaloneSetup(controller).setHandlerExceptionResolvers(
				withBaseControllerAdvice()).build();
	}

	@Test
	public void shouldInterceptException() throws Exception {
		// given
		when(controller.listSpittles(any(Model.class))).thenThrow(
				new IllegalStateException());

		// when
		mockMvc.perform(get("/spittle/list"))

				// then
				.andExpect(
						model().attribute("uuid", matchesPattern(UUID_PATTERN)))
				.andExpect(view().name("exception"))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void shouldReturnNotFoundPageInCaseOfAccessDeniedError()
			throws Exception {
		when(controller.addSpittle(any(Model.class))).thenThrow(
				new AccessDeniedException("No access"));

		// when
		mockMvc.perform(get("/spittle/new"))
				// then
				.andExpect(view().name("forward:/404"))
				.andExpect(status().isNotFound());
	}

	private ExceptionHandlerExceptionResolver withBaseControllerAdvice() {
		ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {

			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(
					final HandlerMethod handlerMethod, final Exception exception) {

				Method method = new ExceptionHandlerMethodResolver(
						BaseController.class).resolveMethod(exception);
				return new ServletInvocableHandlerMethod(new BaseController(),
						method);
			}

		};
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}
}
