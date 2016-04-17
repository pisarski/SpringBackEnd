package com.gmail.sebastian.pisarski.configuration.service.adapter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.service.SpittleService;

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
