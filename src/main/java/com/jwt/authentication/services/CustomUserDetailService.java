package com.jwt.authentication.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		if(userName.equals("Subrat"))
		{
			return new User("Subrat","Nayak",new ArrayList<>());
		}else
		{
			throw new UsernameNotFoundException("User Not Found!");
		}
	}

}
