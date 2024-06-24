package com.inkmelo.bookitem;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.DuplicatedBookItemException;
import com.inkmelo.exception.InvalidBookItemFieldValueException;
import com.inkmelo.exception.NoBookExistException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemExistException;
import com.inkmelo.exception.NoBookItemFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Book Item", description = "Book Item Management APIs")
@RestController
@RequiredArgsConstructor
public class BookItemController {
	private final BookItemService service;
	
	@Operation(summary = "Get Active Book Items Only",
			description = "This endpoint will return all book items that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/store/api/v1/book-items")
	public ResponseEntity<?> getAllActiveBookItem(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false) BookItemType type,
				@RequestParam(required = false) String title
			) {
		
		if (title == null) title = "";
		
		return service.findAllBookItemByStatus(BookItemStatus.ACTIVE, page, size, type, title);
	}
	
	@Operation(summary = "Get Book Items",
			description = "This endpoint will return all book items in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/book-items")
	public ResponseEntity<?> getAllBookItem(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false) BookItemType type,
				@RequestParam(required = false) String title
			) {
		
		if (title == null) title = "";
		
		return service.findAllBookItem(page, size, type, title);
	}
	
	@Operation(summary = "Get All Book Item Type",
			description = "This endpoint will return all book item type | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/book-items/type")
	public Set<BookItemType> getAllBookItemType() {
		return service.findAllBookItemType();
	}
	
	@Operation(summary = "Get Book Item Status",
			description = "This endpoint will return all book item status | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/book-items/status")
	public Set<BookItemStatus> getAllBookItemStatus() {
		return service.findAllBookItemStatus();
	}
	
	@Operation(summary = "Create new Book Item",
			description = "This endpoint will create new book item with the given information | (Authority) ADMIN, MANAGER.")
	@PostMapping("/admin/api/v1/book-items")
	public ResponseEntity<?> saveBookItem(@Valid @RequestBody BookItemCreateBodyDTO bookItemDTO) {
		service.saveBookItem(bookItemDTO);

		return Utils.generateMessageResponseEntity(
				"Tạo mới tài nguyên sách thành công!", 
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Book Item data",
			description = "This endpoint will update book item with the given information | (Authority) ADMIN, MANAGER.")
	@PutMapping("/admin/api/v1/book-items")
	public ResponseEntity<?> updateBookItem(@Valid @RequestBody BookItemUpdateBodyDTO bookItemDTO) {
		service.updateBookItem(bookItemDTO);

		return Utils.generateMessageResponseEntity(
				"Cập nhật tài nguyên sách thành công!", 
				HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Book Item By Id",
			description = "This endpoint will soft delete book item with the given id | (Authority) ADMIN, MANAGER.")
	@DeleteMapping("/admin/api/v1/book-items/{id}")
	public ResponseEntity<?> deleteBookItemById(@PathVariable("id") Integer id) {
		service.deleteBookItemById(id);
		
		return Utils.generateMessageResponseEntity(
				"Xóa tài nguyên sách với mã số " + id + " thành công!", 
				HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoBookItemFoundException.class)
	public ResponseEntity<?> handleNoBookItemFoundException(
			NoBookItemFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookItemExistException.class)
	public ResponseEntity<?> handleNoBookItemExistException(
			NoBookItemExistException ex
			) {

		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookFoundException.class)
	public ResponseEntity<?> handleNoBookFoundException(
			NoBookFoundException ex
			) {

		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicatedBookItemException.class)
	public ResponseEntity<?> handleDuplicatedBookItemException(
				DuplicatedBookItemException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidBookItemFieldValueException.class)
	public ResponseEntity<?> handleInvalidBookItemFieldValueException(
			InvalidBookItemFieldValueException ex
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
