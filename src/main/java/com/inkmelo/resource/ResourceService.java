package com.inkmelo.resource;

import org.springframework.stereotype.Service;

@Service
public class ResourceService {
	private ResourceRepository repository;

	public ResourceService(ResourceRepository repository) {
		this.repository = repository;
	}
	
	
}
