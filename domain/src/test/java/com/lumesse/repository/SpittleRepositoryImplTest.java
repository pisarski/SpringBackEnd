package com.lumesse.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

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
