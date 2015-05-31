package com.lumesse.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumesse.entity.Spittle;
import com.lumesse.repository.SpittleRepository;
import com.lumesse.service.SpittleService;

@Service
public class SpittleServiceImpl implements SpittleService {

	private static final Comparator<Spittle> SPITTLE_TIME_COMPARATOR = new Comparator<Spittle>() {

		@Override
		public int compare(Spittle o1, Spittle o2) {
			return o1.getTime().compareTo(o2.getTime());
		}
	};

	@Autowired
	private SpittleRepository spittleRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Spittle> findAllSorted() {
		return spittleRepository.findAll().stream()
				.sorted(SPITTLE_TIME_COMPARATOR).collect(Collectors.toList());
	}

}
