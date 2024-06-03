package com.inkmelo.category;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoCategoryExistException;
import com.inkmelo.exception.NoCategoryFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category", description = "Category Management APIs")
@RestController
public class CategoryController {
	private CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}
	
	@Operation(summary = "Get All Active Genres",
			description = "This endpoint will return all categories that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/categories")
	public List<CategoryResponseDTO> getAllActiveCategory() {
		return service.findAllCategoryByStatus(CategoryStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Genres",
			description = "This endpoint will return all categories in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/categories")
	public List<CategoryAdminResponseDTO> getAllCategory() {
		return service.findAllCategory();
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoCategoryFoundException.class)
	public ResponseEntity<?> handleNoGenreFoundException1(
				NoCategoryFoundException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCategoryExistException.class)
	public ResponseEntity<?> handleNoGenreExistException(
				NoCategoryExistException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NO_CONTENT.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex
			) {
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex
			) {
		var errors = new HashMap<String, Object>();
		
		errors.put("timestamp", Utils.getCurrentTimestamp());
		errors.put("status", HttpStatus.BAD_REQUEST.value());
		
		ex.getBindingResult().getAllErrors()
			.forEach(e -> {
				var fieldName = ((FieldError) e).getField();
				var errorMsg = e.getDefaultMessage();
				errors.put(fieldName, errorMsg);
			});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
