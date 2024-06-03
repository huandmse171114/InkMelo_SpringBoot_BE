package com.inkmelo.category;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	private CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}
	
	@GetMapping("/categories")
	public List<CategoryResponseDTO> getAllActiveCategory() {
		return service.findAllCategoryByStatus(CategoryStatus.ACTIVE);
	}
	
	@GetMapping("/admin/categories")
	public List<CategoryAdminResponseDTO> getAllCategory() {
		return service.findAllCategory();
	}
	
}
