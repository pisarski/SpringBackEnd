package com.lumesse.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.lumesse.entity.Spittle;

public interface SpittleService {

	long MAX_NUM_OF_SPITTLES = 20;

	/**
	 * @return list of Spittles sorted by time desc.
	 */
	List<Spittle> findAllSorted();

	/**
	 * Saves spittle. If time is not set, spittle is saved with current time. If
	 * number of Spittles will exceed MAX_NUM_OF_SPITTLES then the oldest ones
	 * are removed so that only MAX_NUM_OF_SPITTLES remain.
	 * 
	 * @param spittle
	 *            spittle to save
	 * @return saved spittle
	 */
	@Secured("ROLE_USER")
	Spittle save(Spittle spittle);
}
