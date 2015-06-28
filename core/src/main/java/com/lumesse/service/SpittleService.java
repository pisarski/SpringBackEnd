package com.lumesse.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

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
	@PreAuthorize("(#spittle.id == null and hasAuthority('ADD_SPITTLE')) or (#spittle.id != null and hasAnyAuthority('EDIT_ALL_SPITTLES', 'EDIT_OWN_SPITTLE'))")
	Spittle save(Spittle spittle);

	@PostAuthorize("hasAuthority('EDIT_ALL_SPITTLES') or (hasAuthority('EDIT_OWN_SPITTLE') and principal.user.id == returnObject.createUser.id)")
	Spittle getById(long id);
}
