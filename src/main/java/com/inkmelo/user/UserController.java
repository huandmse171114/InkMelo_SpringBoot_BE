package com.inkmelo.user;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}
	
	
}
