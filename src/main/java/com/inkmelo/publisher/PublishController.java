package com.inkmelo.publisher;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {
	private PublishService service;

	public PublishController(PublishService service) {
		this.service = service;
	}
	
	
}
