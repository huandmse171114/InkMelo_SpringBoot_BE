package com.inkmelo.category;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoCategoryExistException;

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
		var categories = repository.findAll();
		
		if (categories.isEmpty()) {
			throw new NoCategoryExistException("Category data is empty.");
		}
		
		return categories.stream()
				.map(category -> mappingService.categoryToCategoryAdminResponseDTO(category))
				.sorted(new Comparator<CategoryAdminResponseDTO>() {
					@Override
					public int compare(CategoryAdminResponseDTO o1, CategoryAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}

	public List<CategoryResponseDTO> findAllCategoryByStatus(CategoryStatus status) {
		var categories = repository.findAllByStatus(status);
		
		if (categories.isEmpty()) {
			throw new NoCategoryExistException("Category data is empty.");
		}
		
		return categories.stream()
				.map(category -> mappingService.categoryToCategoryResponseDTO(category))
				.sorted(new Comparator<CategoryResponseDTO>() {
					@Override
					public int compare(CategoryResponseDTO o1, CategoryResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}
	
	
}
