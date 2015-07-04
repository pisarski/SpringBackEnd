package com.lumesse.configuration.service.adapter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lumesse.entity.Spittle;
import com.lumesse.entity.User;
import com.lumesse.service.SpittleService;

@Service("spittleServiceAdapter")
public class SpittleServiceAdapter implements SpittleService {

	@Override
	public List<Spittle> findAllSorted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spittle save(Spittle spittle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spittle getById(long id) {
		Spittle spittle = new Spittle();
		spittle.setId(id);

		User createdUser = new User();
		createdUser.setId(1L);

		spittle.setCreateUser(createdUser);

		return spittle;
	}

}
