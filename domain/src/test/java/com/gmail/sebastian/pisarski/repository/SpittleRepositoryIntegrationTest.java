package com.gmail.sebastian.pisarski.repository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gmail.sebastian.pisarski.builder.SpittleBuilder;
import com.gmail.sebastian.pisarski.configuration.DevDbConfig;
import com.gmail.sebastian.pisarski.configuration.DomainConfiguration;
import com.gmail.sebastian.pisarski.entity.Spittle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DomainConfiguration.class, DevDbConfig.class })
@ActiveProfiles("test")
public class SpittleRepositoryIntegrationTest {

	@Autowired
	private SpittleRepository spittleRepository;

	@Test
	public void shouldFindAllSpittles() {
		// given
		Spittle spittle = new SpittleBuilder().withMessage("msg").withTitle("title").withTime(new Date()).build();
		spittleRepository.save(spittle);

		// when
		List<Spittle> spittles = spittleRepository.findAll();

		// then
		assertThat(spittles, not(empty()));
	}

}
