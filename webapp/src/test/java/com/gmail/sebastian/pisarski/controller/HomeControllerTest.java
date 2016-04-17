package com.gmail.sebastian.pisarski.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gmail.sebastian.pisarski.configuration.SecurityWebInitializer;
import com.gmail.sebastian.pisarski.configuration.TestSecurityConfig;
import com.gmail.sebastian.pisarski.controller.HomeController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSecurityConfig.class,
		SecurityWebInitializer.class })
public class HomeControllerTest {

	@Autowired
	private Filter springSecurityFilterChain;

	@Mock
	private Environment env;

	@InjectMocks
	private HomeController homeController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(homeController).addFilters(
				springSecurityFilterChain).build();
	}

	@Test
	public void shouldReturnHomeViewOnGet() throws Exception {
		// when
		mockMvc.perform(get("/"))

				// then
				.andExpect(redirectedUrl("/spittle/list"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void shouldReturnLoginView() throws Exception {
		// when
		mockMvc.perform(get("/login"))

				// then
				.andExpect(model().attribute("hideLoginLink", true))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnLoginViewWithErrors() throws Exception {
		// when
		mockMvc.perform(get("/login").param("error", "some error"))

				// then
				.andExpect(model().attribute("hideLoginLink", true))
				.andExpect(model().attribute("error", true))
				.andExpect(status().isOk());
	}
}
