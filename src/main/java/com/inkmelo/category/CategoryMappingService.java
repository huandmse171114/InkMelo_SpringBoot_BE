package com.inkmelo.category;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
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
				.status(category.getStatus())
				.build();
	}

	public CategoryResponseDTO categoryToCategoryResponseDTO(Category category) {
		return CategoryResponseDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}
	
	public Category categoryCreateBodyDTOToCategory(CategoryCreateBodyDTO categoryDTO) {
		return Category.builder()
				.name(categoryDTO.name())
				.description(categoryDTO.description())
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy(SecurityContextHolder.getContext()
						.getAuthentication().getName())
				.status(CategoryStatus.ACTIVE)
				.build();
	}

}
