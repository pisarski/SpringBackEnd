package com.lumesse.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumesse.entity.Spittle;
import com.lumesse.repository.SpittleRepository;
import com.lumesse.service.SpittleService;

@Service
@Transactional
public class SpittleServiceImpl implements SpittleService {

	@Autowired
	private SpittleRepository spittleRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Spittle> findAllSorted() {
		return spittleRepository.findAll().stream()
				.sorted((s1, s2) -> s2.getTime().compareTo(s1.getTime()))
				.collect(Collectors.toList());
	}

	@Override
	public Spittle save(Spittle spittle) {
		long numberOfSpittles = spittleRepository.count();
		if (numberOfSpittles >= MAX_NUM_OF_SPITTLES) {
			long numberOfSpittlesToRemove = numberOfSpittles
					- MAX_NUM_OF_SPITTLES + 1;
			removeOldestSpittle((int) numberOfSpittlesToRemove);
		}
		if (spittle.getTime() == null) {
			spittle.setTime(new Date());
		}
		return spittleRepository.save(spittle);
	}

	private void removeOldestSpittle(int numberOfSpittlesToRemove) {
		Page<Spittle> result = spittleRepository.findAll(new PageRequest(0,
				numberOfSpittlesToRemove, Direction.ASC, "time"));
		List<Spittle> oldestSpittles = result.getContent();
		spittleRepository.delete(oldestSpittles);
	}

}
