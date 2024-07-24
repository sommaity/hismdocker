package com.hims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hims.repository.UserRepository;
import com.hims.model.User;

@Service
public class MyUserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	public User loadUserByUsername(String s){
		User user=null;
		user=getUserByEmail(s);
		return user;
	}
	
	private User getUserByEmail(String s){
		User user=userRepo.findUserByEmail(s);
		return user;
	}

}
