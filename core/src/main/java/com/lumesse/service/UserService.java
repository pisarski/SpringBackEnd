package com.lumesse.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.lumesse.entity.User;

@Secured("ROLE_ADMIN")
public interface UserService {

	long MAX_USERS_COUNT = 20L;

	List<User> findAll();

	long getNumberOfUsers();

	User save(User user);

	User findByUsername(String username);
}
