package com.codewithdurgesh.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.InvalidCredentialException;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repositories.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loading User from DB by username
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email: "+username, 1l));
		return user;
	}

}
