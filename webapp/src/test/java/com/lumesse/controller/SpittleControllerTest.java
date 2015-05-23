package com.lumesse.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lumesse.repository.SpittleRepository;

@RunWith(MockitoJUnitRunner.class)
public class SpittleControllerTest {

	@Mock
	private SpittleRepository spittleRepository;

	@InjectMocks
	private SpittleController spittleController;

	@Test
	public void shouldReturnProperViewWithSpittleListInModel() throws Exception {
		// given
		standaloneSetup(spittleController).build()

				// when
				.perform(get("/spittle/list"))

				// then
				.andExpect(model().attributeExists("spittles"))
				.andExpect(view().name("spittles"));
	}
}
