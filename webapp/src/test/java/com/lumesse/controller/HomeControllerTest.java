package com.lumesse.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class HomeControllerTest {

	private HomeController homeController;

	@Before
	public void setUp() {
		homeController = new HomeController();
	}

	@Test
	public void shouldReturnHomeViewOnGet() throws Exception {
		MockMvc mockMvc = standaloneSetup(homeController).build();

		mockMvc.perform(get("/")).andExpect(view().name("home"));
	}
}
