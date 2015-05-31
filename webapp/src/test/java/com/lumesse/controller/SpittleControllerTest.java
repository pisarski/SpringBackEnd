package com.lumesse.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lumesse.entity.Spittle;
import com.lumesse.service.SpittleService;

@RunWith(MockitoJUnitRunner.class)
public class SpittleControllerTest {

	@Mock
	private SpittleService spittleService;

	@InjectMocks
	private SpittleController spittleController;

	@Test
	public void shouldReturnProperViewWithSpittleListInModel() throws Exception {
		// given
		List<Spittle> spittles = new ArrayList<>();
		when(spittleService.findAllSorted()).thenReturn(spittles);

		standaloneSetup(spittleController).build()

				// when
				.perform(get("/spittle/list"))

				// then
				.andExpect(model().attribute("spittles", spittles))
				.andExpect(view().name("spittle.list"));
	}
}
