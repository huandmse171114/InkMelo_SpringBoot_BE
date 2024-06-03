package com.inkmelo.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryMappingService {

	public CategoryAdminResponseDTO categoryToCategoryAdminResponseDTO(Category category) {
		return CategoryAdminResponseDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.createdAt(category.getCreatedAt())
				.lastChangedBy(category.getLastChangedBy())
				.lastUpdatedTime(category.getLastUpdatedTime())
				.build();
	}

	public CategoryResponseDTO categoryToCategoryResponseDTO(Category category) {
		return CategoryResponseDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}

}
