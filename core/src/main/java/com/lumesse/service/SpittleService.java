package com.lumesse.service;

import java.util.List;

import com.lumesse.entity.Spittle;

public interface SpittleService {

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
