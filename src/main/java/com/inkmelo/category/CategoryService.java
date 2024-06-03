package com.inkmelo.category;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoCategoryExistException;
import com.inkmelo.exception.NoCategoryFoundException;

import jakarta.validation.Valid;

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
	
	public Set<CategoryStatus> findAllCategoryStatus() {
		return CategoryStatus.allStatus;
	}

	public void saveCategory(@Valid CategoryCreateBodyDTO categoryDTO) throws DataIntegrityViolationException {
		Category category = mappingService.categoryCreateBodyDTOToCategory(categoryDTO);
		
		repository.save(category);
	}

	public void updateCategory(CategoryUpdateBodyDTO categoryDTO) throws DataIntegrityViolationException {
		var categoryOption = repository.findById(categoryDTO.id());
		
		if (categoryOption.isEmpty()) {
			throw new NoCategoryFoundException(categoryDTO.id());
		}
		
		Category category = categoryOption.get();
		category.setName(categoryDTO.name());
		category.setDescription(categoryDTO.description());
		category.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		category.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		category.setStatus(categoryDTO.status());
		
		repository.save(category);
	}
	
	public void deleteCategoryById(Integer id) {
		var categoryOption = repository.findById(id);
		
		if (categoryOption.isEmpty()) {
			throw new NoCategoryFoundException(id);
		}
		
		Category category = categoryOption.get();
		category.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		category.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		category.setStatus(CategoryStatus.INACTIVE);
		
		repository.save(category);
	}

	
	
}
