package com.lumesse.service;

import java.util.List;

import com.lumesse.entity.Spittle;

public interface SpittleService {

	long MAX_NUM_OF_SPITTLES = 20;

	/**
	 * @return list of Spittles sorted by time desc.
	 */
	List<Spittle> findAllSorted();

	/**
	 * Saves spittle. If time is not set, spittle is saved with current time.
	 * 
	 * @param spittle
	 *            spittle to save
	 * @return saved spittle
	 */
	Spittle save(Spittle spittle);
}
