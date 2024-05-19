package com.inkmelo.category;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	private CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}
	
	
}
