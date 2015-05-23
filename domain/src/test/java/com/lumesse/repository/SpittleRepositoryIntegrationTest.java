package com.lumesse.repository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lumesse.Spittle;
import com.lumesse.configuration.DomainConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainConfiguration.class)
public class SpittleRepositoryIntegrationTest {

	@Autowired
	private SpittleRepository spittleRepository;

	@Test
	public void shouldFindAllSpittles() {
		// when
		List<Spittle> spittles = spittleRepository.findSpittles();

		// then
		assertThat(spittles, not(empty()));
	}
}
