package com.target.interview.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.target.interview.util.ApplicationConstant;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if(ApplicationConstant.TARGET.equals(username)) {
			return new User("target","$2y$12$ov2egAb50Gwr8VP5eKU.ueBAk62/15a/IQIZly7teMGhQ63q/v9ly",new ArrayList<>()); 
		}else {
		throw new UsernameNotFoundException("User not found");
		}
	}

}
