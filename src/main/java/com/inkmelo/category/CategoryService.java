package com.inkmelo.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	private CategoryRepository repository;

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}
	
	
}
