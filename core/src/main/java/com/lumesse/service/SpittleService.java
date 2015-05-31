package com.lumesse.service;

import java.util.List;

import com.lumesse.entity.Spittle;

public interface SpittleService {

	/**
	 * @return list of Spittles sorted by time asc.
	 */
	List<Spittle> findAllSorted();
}
