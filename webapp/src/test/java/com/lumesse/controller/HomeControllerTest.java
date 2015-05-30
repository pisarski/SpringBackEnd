package com.lumesse.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	@Mock
	private Environment env;

	@InjectMocks
	private HomeController homeController;

	@Test
	public void shouldReturnHomeViewOnGet() throws Exception {
		// given
		String envValue = "ENV";
		when(env.getProperty(anyString())).thenReturn(envValue);

		MockMvc mockMvc = standaloneSetup(homeController).build();

		// when
		mockMvc.perform(get("/"))

				// then
				.andExpect(model().attribute("env", envValue))
				.andExpect(view().name("home"));
	}
}
