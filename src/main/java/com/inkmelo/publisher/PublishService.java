package com.inkmelo.publisher;

import org.springframework.stereotype.Service;

@Service
public class PublishService {
	private PublishRepository repository;

	public PublishService(PublishRepository repository) {
		this.repository = repository;
	}
	
	
}
