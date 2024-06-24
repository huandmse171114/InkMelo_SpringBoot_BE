package com.inkmelo.category;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.catalina.connector.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoCategoryExistException;
import com.inkmelo.exception.NoCategoryFoundException;
import com.inkmelo.utils.Utils;

import jakarta.validation.Valid;

@Service
public class CategoryService {
	private final CategoryRepository repository;
	private final CategoryMappingService mappingService;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;

	public CategoryService(CategoryRepository repository, CategoryMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}
	
public ResponseEntity<?> findAllCategory(Integer page, Integer size, String keyword) {
		
			
//		Get categories, no paging
		if (page == null & size == null) {
			var categories = repository.findAllByNameContainingIgnoreCase(keyword);
			
			if (categories.isEmpty()) {
				throw new NoCategoryExistException("Dữ liệu về danh mục hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(categories.stream()
					.map(category -> mappingService.categoryToCategoryAdminResponseDTO(category))
					.sorted(new Comparator<CategoryAdminResponseDTO>() {
						@Override
						public int compare(CategoryAdminResponseDTO o1, CategoryAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);	
				
//		Get categories, with paging, with search
		}else {
			
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			var pageCategories = repository.findAllByNameContainingIgnoreCase(keyword, paging);
			
			return getCategoryAdminResponseDTO(pageCategories);
		}
		
		
	}

	public ResponseEntity<?> findAllCategoryByStatus(CategoryStatus status, Integer page, Integer size, String keyword)
		throws NoCategoryExistException {
		
//		Get categories, no paging
		if (page == null & size == null) {
			var categories = repository.findAllByStatusAndNameContainingIgnoreCase(status, keyword);
			
			if (categories.isEmpty()) {
				throw new NoCategoryExistException("Dữ liệu về danh mục hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(categories.stream()
					.map(category -> mappingService.categoryToCategoryResponseDTO(category))
					.sorted(new Comparator<CategoryResponseDTO>() {
						@Override
						public int compare(CategoryResponseDTO o1, CategoryResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);		
			
//		Get categories, with paging, with search
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			var pageCategories = repository.findAllByStatusAndNameContainingIgnoreCase(status, keyword, paging);
			
			return getCategoryResponseDTO(pageCategories);
		}
		

	}
	
	public Set<CategoryStatus> findAllCategoryStatus() {
		return CategoryStatus.allStatus;
	}

	public void saveCategory(CategoryCreateBodyDTO categoryDTO) 
			throws DataIntegrityViolationException {
		Category category = mappingService.categoryCreateBodyDTOToCategory(categoryDTO);
		
		repository.save(category);
	}

	public void updateCategory(CategoryUpdateBodyDTO categoryDTO) 
			throws DataIntegrityViolationException {
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
	
	public void deleteCategoryById(Integer id) 
		throws NoCategoryFoundException {
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

	
	private ResponseEntity<?> getCategoryAdminResponseDTO(Page<Category> pageCategories) {
		var categories = pageCategories.getContent();
		
		if (categories.isEmpty()) {
			throw new NoCategoryExistException("Dữ liệu về danh mục hiện đang rỗng.");
		}
		
		var response = categories.stream()
				.map(category -> mappingService.categoryToCategoryAdminResponseDTO(category))
				.sorted(new Comparator<CategoryAdminResponseDTO>() {
					@Override
					public int compare(CategoryAdminResponseDTO o1, CategoryAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
		
		return Utils.generatePagingListResponseEntity(
				pageCategories.getTotalElements(), 
				response, 
				pageCategories.getTotalPages(), 
				pageCategories.getNumber(),
				HttpStatus.OK);
	}
	
	private ResponseEntity<?> getCategoryResponseDTO(Page<Category> pageCategories) {
		var categories = pageCategories.getContent();
		
		if (categories.isEmpty()) {
			throw new NoCategoryExistException("Dữ liệu về danh mục hiện đang rỗng.");
		}
		
		var response = categories.stream()
				.map(category -> mappingService.categoryToCategoryResponseDTO(category))
				.sorted(new Comparator<CategoryResponseDTO>() {
					@Override
					public int compare(CategoryResponseDTO o1, CategoryResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
		
		return Utils.generatePagingListResponseEntity(
				pageCategories.getTotalElements(), 
				response, 
				pageCategories.getTotalPages(), 
				pageCategories.getNumber(),
				HttpStatus.OK);
	}
	
	
	
}
