package com.gmail.sebastian.pisarski.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.gmail.sebastian.pisarski.entity.User;

@PreAuthorize("hasAuthority('USER_MANAGEMENT')")
public interface UserService {

	long MAX_USERS_COUNT = 20L;

	/**
	 * @return all users
	 */
	List<User> findAll();

	/**
	 * @return number of all users
	 */
	long getNumberOfUsers();

	/**
	 * Saves new user or updates existing one.
	 * 
	 * @param user
	 *            user to save or edit
	 * @return saved user
	 */
	@PreAuthorize("hasAuthority('USER_MANAGEMENT') and principal.user.id != #user.id")
	User save(User user);

	/**
	 * @return user with given username or null if such user doesn't exist
	 */
	User findByUsername(String username);

	@PostAuthorize("hasAuthority('USER_MANAGEMENT') and principal.user.id != returnObject.id")
	User getById(long id);
}
