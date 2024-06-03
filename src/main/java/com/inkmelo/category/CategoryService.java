package com.inkmelo.category;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	private final CategoryRepository repository;
	private final CategoryMappingService mappingService;

	public CategoryService(CategoryRepository repository, CategoryMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public List<CategoryAdminResponseDTO> findAllCategory() {
		return repository.findAll()
				.stream()
				.map(category -> mappingService.categoryToCategoryAdminResponseDTO(category))
				.toList();
	}

	public List<CategoryResponseDTO> findAllCategoryByStatus(CategoryStatus status) {
		return repository.findAllByStatus(status)
				.stream()
				.map(category -> mappingService.categoryToCategoryResponseDTO(category))
				.toList();
	}
	
	
}
