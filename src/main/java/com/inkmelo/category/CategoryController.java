package com.inkmelo.category;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoCategoryExistException;
import com.inkmelo.exception.NoCategoryFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category", description = "Category Management APIs")
@RestController
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService service;
	
	@Operation(summary = "Get All Active Categories",
			description = "This endpoint will return all categories that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/store/api/v1/categories")
	public List<CategoryResponseDTO> getAllActiveCategory() {
		return service.findAllCategoryByStatus(CategoryStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Categories",
			description = "This endpoint will return all categories in DB | (Authority) ADMIN.")
	@GetMapping("/admin/api/v1/categories")
	public List<CategoryAdminResponseDTO> getAllCategory() {
		return service.findAllCategory();
	}
	
	@Operation(summary = "Get All Category's Status",
			description = "This endpoint will return all category's status | (Authority) ADMIN.")
	@GetMapping("/admin/api/v1/categories/status")
	public Set<CategoryStatus> getAllCategoryStatus() {
		return service.findAllCategoryStatus();
	}
	
	@Operation(summary = "Create new Category",
			description = "This endpoint will create new category with the given information | (Authority) ADMIN.")
	@PostMapping("/admin/api/v1/categories")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryCreateBodyDTO categoryDTO) {
		service.saveCategory(categoryDTO);
		
		return Utils.generateMessageResponseEntity(
				"Tạo mới danh mục thành công!", 
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Category data",
			description = "This endpoint will update category with the given information | (Authority) ADMIN.")
	@PutMapping("/admin/api/v1/categories")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryUpdateBodyDTO categoryDTO) {
		service.updateCategory(categoryDTO);

		return Utils.generateMessageResponseEntity(
				"Cập nhật danh mục thành công!", 
				HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Category By Id",
			description = "This endpoint will soft delete category with the given id | (Authority) ADMIN.")
	@DeleteMapping("/admin/api/v1/categories/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Integer id){
		service.deleteCategoryById(id);

		return Utils.generateMessageResponseEntity(
				"Xóa danh mục với mã số " + id + " thành công!", 
				HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoCategoryFoundException.class)
	public ResponseEntity<?> handleNoCategoryFoundException(
				NoCategoryFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCategoryExistException.class)
	public ResponseEntity<?> handleNoCategoryExistException(
				NoCategoryExistException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
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
