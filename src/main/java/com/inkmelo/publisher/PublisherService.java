package com.inkmelo.publisher;

import org.springframework.stereotype.Service;

@Service
public class PublisherService {
	private PublisherRepository repository;

	public PublisherService(PublisherRepository repository) {
		this.repository = repository;
	}
	
	
}
