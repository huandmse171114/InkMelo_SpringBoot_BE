package com.inkmelo.bookpackage;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemFoundException;
import com.inkmelo.exception.NoBookPackageExistException;
import com.inkmelo.exception.NoBookPackageFoundException;
import com.inkmelo.exception.NoCategoryFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Book Package", description = "Book Package Management APIs")
@RestController
@RequiredArgsConstructor
public class BookPackageController {
	private final BookPackageService service;
	
	
	@Operation(summary = "Get All Active Book Packages",
			description = "This endpoint will return all book packages that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/store/api/v1/book-packages")
	public List<BookPackageResponseDTO> getAllActiveBookPackage() {
		return service.findAllBookPackageByStatus(BookPackageStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Book Packages",
			description = "This endpoint will return all book packages in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/book-packages")
	public List<BookPackageAdminResponseDTO> getAllBookPackage() {
		return service.findAllBookPackage();
	}
	
	@Operation(summary = "Get All Book Package Status",
			description = "This endpoint will return all book package status | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/book-packages/status")
	public Set<BookPackageStatus> getAllBookPackageStatus() {
		return service.findAllBookPackageStatus();
	}
	

	@Operation(summary = "Get Book Package Modes",
			description = "This endpoint will return all book package modes | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/book-packages/mode")
	public List<BookPackageModeResponseDTO> getAllBookPackageMode() {
		return service.findAllBookPackageMode();
	}
	
	@Operation(summary = "Search all Book Packages",
			description = "This endpoint will return all book packages that have ACTIVE status and corresponding search value in DB | (Authority) ALL.")
	@GetMapping("/store/api/v1/book-packages/search")
	public List<BookPackageResponseDTO> findAllBookPackageByCategory(
				@RequestParam(name = "category", required = false) Integer categoryId,
				@RequestParam(name = "mode", required = false) Integer modeId,
				@RequestParam(name = "query", required = false) String keyword
			) {
		return service.findAllBookPackageByCriteria(categoryId, modeId, keyword);
	}
	
	@Operation(summary = "Create new Book Package",
			description = "This endpoint will create new book package with the given information | (Authority) ADMIN, MANAGER.")
	@PostMapping("/admin/api/v1/book-packages")
	public ResponseEntity<?> saveBookPackage(@Valid @RequestBody BookPackageCreateBodyDTO bookPackageDTO) {
		
		service.saveBookPackage(bookPackageDTO);
		
		return Utils.generateMessageResponseEntity(
				"Tạo mới gói tài nguyên sách thành công!", 
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Book Package data",
			description = "This endpoint will update book package with the given information | (Authority) ADMIN, MANAGER.")
	@PutMapping("/admin/api/v1/book-packages")
	public ResponseEntity<?> updateBookPackage(@Valid @RequestBody BookPackageUpdateBodyDTO bookPackageDTO) {
		service.updateBookPackage(bookPackageDTO);
		
		return Utils.generateMessageResponseEntity(
				"Cập nhật gói tài nguyên sách thành công!",
				HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Book Package By Id",
			description = "This endpoint will soft delete book package with the given id | (Authority) ADMIN, MANAGER.")
	@DeleteMapping("/admin/api/v1/book-packages/{id}")
	public ResponseEntity<?> deleteBookPackageById(@PathVariable("id") Integer id) {
		service.deleteBookPackageById(id);
		
		return Utils.generateMessageResponseEntity(
				"Xóa gói tài nguyên sách với mã số " + id 
				+ " thành công!", 
				HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoBookPackageFoundException.class)
	public ResponseEntity<?> handleNoBookPackageFoundException(
			NoBookPackageFoundException ex
			) {

		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookPackageExistException.class)
	public ResponseEntity<?> handleNoBookPackageExistException(
			NoBookPackageExistException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(
			IllegalArgumentException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoBookFoundException.class)
	public ResponseEntity<?> handleNoBookFoundException(
			NoBookFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookItemFoundException.class)
	public ResponseEntity<?> handleNoBookItemFoundException(
			NoBookItemFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCategoryFoundException.class)
	public ResponseEntity<?> handleNoCategoryFoundException(
			NoCategoryFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
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
