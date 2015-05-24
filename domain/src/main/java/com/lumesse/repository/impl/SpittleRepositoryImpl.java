package com.lumesse.repository.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.lumesse.entity.Spittle;
import com.lumesse.repository.SpittleRepository;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

	private String spittleListFileName = "spittlesList.json";

	private Comparator<Spittle> SPITTLE_TIME_COMPARATOR = new Comparator<Spittle>() {

		@Override
		public int compare(Spittle o1, Spittle o2) {
			return o1.getTime().compareTo(o2.getTime());
		}
	};

	@Override
	public List<Spittle> findSpittles() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return Arrays
					.asList(mapper.readValue(getClass().getClassLoader()
							.getResourceAsStream(spittleListFileName),
							Spittle[].class)).stream()
					.sorted(SPITTLE_TIME_COMPARATOR)
					.collect(Collectors.toList());

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
