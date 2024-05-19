package com.inkmelo.publisher;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {
	private PublisherService service;

	public PublisherController(PublisherService service) {
		this.service = service;
	}
	
	
}
