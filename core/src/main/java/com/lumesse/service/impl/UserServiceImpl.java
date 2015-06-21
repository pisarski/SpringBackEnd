package com.lumesse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumesse.entity.User;
import com.lumesse.repository.UserRepository;
import com.lumesse.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			fetchUserData(user);
		}
		return users;
	}

	private void fetchUserData(User user) {
		user.getRoles().size();
	}

	@Override
	public User save(User user) {
		if (userRepository.count() >= MAX_USERS_COUNT) {
			throw new IllegalStateException("Cannot create more than "
					+ MAX_USERS_COUNT + " users.");
		}

		if (user.getId() == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} else {
			User existingUser = userRepository.findOne(user.getId());
			if (!existingUser.getPassword().equals(user.getPassword())) {
				throw new IllegalArgumentException("password cannot be changed");
			}
		}
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	@Override
	public long getNumberOfUsers() {
		return userRepository.count();
	}

	@Transactional(readOnly = true)
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
