package com.inkmelo.resource;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
	private ResourceService service;

	public ResourceController(ResourceService service) {
		this.service = service;
	}
	
}
