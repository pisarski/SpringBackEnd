package com.lumesse.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public class ErrorControllerTest {

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = standaloneSetup(new ErrorController()).build();
	}

	@Test
	public void shouldInterceptException() throws Exception {
		// when
		mockMvc.perform(get("/404"))

				// then
				.andExpect(model().attribute("messageCode", notNullValue()))
				.andExpect(view().name("errorCode"))
				.andExpect(status().isNotFound());

	}
}
