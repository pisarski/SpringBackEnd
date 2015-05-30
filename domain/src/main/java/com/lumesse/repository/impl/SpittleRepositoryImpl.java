package com.lumesse.repository.impl;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.lumesse.entity.Spittle;
import com.lumesse.repository.SpittleRepository;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

	private String spittleListFileName = "spittlesList.json";

	@Override
	public List<Spittle> findSpittles() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return Arrays
					.asList(mapper.readValue(getClass().getClassLoader()
							.getResourceAsStream(spittleListFileName),
							Spittle[].class));

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
