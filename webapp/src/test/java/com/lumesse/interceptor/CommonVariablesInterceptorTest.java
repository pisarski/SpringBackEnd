package com.lumesse.interceptor;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;

import com.lumesse.controller.SpittleController;

@RunWith(MockitoJUnitRunner.class)
public class CommonVariablesInterceptorTest {

	@Mock
	private SpittleController controller;

	@Mock
	private Environment env;

	@InjectMocks
	private CommonVariablesInterceptor commonVariablesInterceptor;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = standaloneSetup(controller).addInterceptors(
				commonVariablesInterceptor).build();
	}

	@Test
	public void shouldIntercept() throws Exception {
		// given
		String appRev = "test_rev_value";
		when(env.getProperty("appRev")).thenReturn(appRev);

		// when
		mockMvc.perform(get("/spittle/list"))

		// then
				.andExpect(model().attribute("appRev", appRev));
	}
}
