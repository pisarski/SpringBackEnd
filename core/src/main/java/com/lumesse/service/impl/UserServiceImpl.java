package com.lumesse.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lumesse.entity.User;
import com.lumesse.exception.ErrorsContainer;
import com.lumesse.repository.UserRepository;
import com.lumesse.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		ErrorsContainer errors = new ErrorsContainer();

		if (userRepository.count() >= MAX_USERS_COUNT) {
			errors.add(null, "user.error.maxNumExceeded", MAX_USERS_COUNT);
		}

		if (user.getId() == null) {
			if (StringUtils.isEmpty(user.getPassword())) {
				errors.add("password", "NotNull");
			} else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
		} else {
			User existingUser = getById(user.getId());
			user.setPassword(existingUser.getPassword());
		}

		if (!usernameIsUnique(user)) {
			errors.add("username", "user.username.Unique");
		}

		validate(user, errors);

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

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User getById(long id) {
		return userRepository.findOne(id);
	}

	private boolean usernameIsUnique(User user) {
		User existingUser = findByUsername(user.getUsername());
		return existingUser == null
				|| existingUser.getId().equals(user.getId());
	}

}
