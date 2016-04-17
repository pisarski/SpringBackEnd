package com.gmail.sebastian.pisarski.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;
import com.gmail.sebastian.pisarski.pojo.SpittleUserDetails;
import com.gmail.sebastian.pisarski.repository.SpittleRepository;
import com.gmail.sebastian.pisarski.service.SpittleService;

@Service
@Transactional
public class SpittleServiceImpl extends BaseService implements SpittleService {

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
		Spittle toSave;
		User loggedUser = getLoggedUser();
		if (spittle.getId() == null) {
			spittle.setCreateUser(loggedUser);
			spittle.setTime(new Date());
			toSave = spittle;
		} else {
			Spittle existingSpittle = getById(spittle.getId());
			checkRightsToEditSpittle(loggedUser, existingSpittle);
			existingSpittle.setMessage(spittle.getMessage());
			existingSpittle.setTitle(spittle.getTitle());
			existingSpittle.setEditUser(loggedUser);
			existingSpittle.setUpdateTime(new Date());
			toSave = existingSpittle;
		}
		validate(toSave);
		return spittleRepository.save(toSave);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Spittle getById(long id) {
		return spittleRepository.findOne(id);
	}

	private void removeOldestSpittle(int numberOfSpittlesToRemove) {
		Page<Spittle> result = spittleRepository.findAll(new PageRequest(0,
				numberOfSpittlesToRemove, Direction.ASC, "time"));
		List<Spittle> oldestSpittles = result.getContent();
		spittleRepository.delete(oldestSpittles);
	}

	private User getLoggedUser() {
		SpittleUserDetails userDetails = (SpittleUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDetails.getUser();
	}

	private void checkRightsToEditSpittle(User loggedUser,
			Spittle existingSpittle) {
		if (!loggedUser.getRights().contains(UserRight.EDIT_ALL_SPITTLES)
				&& !existingSpittle.getCreateUser().getId()
						.equals(loggedUser.getId())) {
			throw new AccessDeniedException("User with id = "
					+ loggedUser.getId()
					+ "donesn't have access to Spittle with id = "
					+ existingSpittle.getId());
		}
	}
}
