package com.lumesse.repository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lumesse.configuration.DevDbConfig;
import com.lumesse.configuration.DomainConfiguration;
import com.lumesse.entity.Spittle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, DevDbConfig.class })
@ActiveProfiles("test")
public class SpittleRepositoryIntegrationTest {

	@Autowired
	private SpittleRepository spittleRepository;

	@Test
	public void shouldFindAllSpittles() {
		// given
		Spittle spittle = new Spittle();
		spittleRepository.save(spittle);

		// when
		List<Spittle> spittles = spittleRepository.findAll();

		// then
		assertThat(spittles, not(empty()));
	}

}
