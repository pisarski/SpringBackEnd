package com.gmail.sebastian.pisarski.configuration.service.adapter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gmail.sebastian.pisarski.service.UserAuthService;

@Service("userAuthServiceAdapter")
public class UserAuthServiceAdapter implements UserAuthService {

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
