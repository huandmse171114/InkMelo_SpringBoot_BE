package com.inkmelo.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	
}
