package com.lumesse.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lumesse.entity.Spittle;
import com.lumesse.repository.SpittleRepository;

@RunWith(MockitoJUnitRunner.class)
public class SplittleServiceImplTest {

	@Mock
	private SpittleRepository spittleRepository;

	@InjectMocks
	private SpittleServiceImpl spittleService;

	@Test
	public void shouldReturnAllSplittlesInProperOrder() {
		// given
		List<Spittle> spittles = new ArrayList<Spittle>();
		spittles.add(getSpittle(toDate("28-01-2015"), "M2"));
		spittles.add(getSpittle(toDate("01-01-2000"), "M0"));
		spittles.add(getSpittle(toDate("29-01-2015"), "M3"));
		spittles.add(getSpittle(toDate("06-06-2002"), "M1"));

		when(spittleRepository.findAll()).thenReturn(spittles);

		// when
		List<Spittle> result = spittleService.findAllSorted();

		// then
		assertThat(result, hasSize(4));
		for (int i = 0; i < result.size(); i++) {
			assertThat(result.get(i).getMessage(), equalTo("M"
					+ (result.size() - 1 - i)));
		}
	}

	@Test
	public void shouldSaveSpittle() {
		// given
		Spittle spittle = getSpittle(new Date(), "msg");
		Spittle savedSpittle = new Spittle();
		when(spittleRepository.save(spittle)).thenReturn(savedSpittle);

		// when
		Spittle result = spittleService.save(spittle);

		// then
		assertEquals(savedSpittle, result);
		verify(spittleRepository).save(spittle);
	}

	@Test
	public void shouldSetTimeBeforeSave() {
		// given
		Spittle spittle = new Spittle();
		spittle.setMessage("message");
		spittle.setTitle("title");

		// when
		spittleService.save(spittle);

		// then
		ArgumentCaptor<Spittle> spittleCaptor = ArgumentCaptor
				.forClass(Spittle.class);
		verify(spittleRepository).save(spittleCaptor.capture());
		Spittle captured = spittleCaptor.getValue();
		assertNotNull(captured.getTime());
		assertEquals(spittle.getTitle(), captured.getTitle());
		assertEquals(spittle.getMessage(), captured.getMessage());
	}

	private Spittle getSpittle(Date date, String msg) {
		Spittle spittle = new Spittle();
		spittle.setMessage(msg);
		spittle.setTime(date);
		return spittle;
	}

	private Date toDate(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			return format.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
