package com.lumesse.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import com.lumesse.entity.Spittle;
import com.lumesse.repository.impl.SpittleRepositoryImpl;

public class SpittleRepositoryImplTest {

	private SpittleRepositoryImpl spittleRepository;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() {
		this.spittleRepository = new SpittleRepositoryImpl();
	}

	@Test
	public void shouldReturnAllSplittlesInProperOrder() {
		// given
		String testFile = "testSpittlesList.json";
		ReflectionTestUtils.setField(spittleRepository, "spittleListFileName",
				testFile, String.class);

		// when
		List<Spittle> spittles = spittleRepository.findSpittles();

		// then
		assertThat(spittles, hasSize(3));
		for (int i = 0; i < spittles.size(); i++) {
			assertThat(spittles.get(i).getMessage(), equalTo("M" + i));
		}
	}

	@Test
	public void shouldThrowExceptionIfFileNotFound() {
		// given
		String nonExistingFile = "nonExisting";
		ReflectionTestUtils.setField(spittleRepository, "spittleListFileName",
				nonExistingFile, String.class);

		// then
		expected.expect(IllegalStateException.class);

		// when
		spittleRepository.findSpittles();
	}
}
