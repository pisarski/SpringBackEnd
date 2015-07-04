package com.lumesse.configuration.service.adapter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lumesse.entity.User;
import com.lumesse.service.UserService;

@Service("userServiceAdapter")
public class UserServiceAdapter implements UserService {

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getNumberOfUsers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(long id) {
		User user = new User();
		user.setId(1L);
		return user;
	}

}
