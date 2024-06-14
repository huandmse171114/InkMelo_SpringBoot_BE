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
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.DuplicatedBookItemException;
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
	
	@Operation(summary = "Get All Active Book Items",
			description = "This endpoint will return all book items that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/book-items")
	public List<BookItemResponseDTO> getAllActiveBookItem() {
		return service.findAllBookItemByStatus(BookItemStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Book Items",
			description = "This endpoint will return all book items in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/book-items")
	public List<BookItemAdminResponseDTO> getAllBookItem() {
		return service.findAllBookItem();
	}
	
	@Operation(summary = "Get All Book Item Type",
			description = "This endpoint will return all book item type | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/book-items/type")
	public Set<BookItemType> getAllBookItemType() {
		return service.findAllBookItemType();
	}
	
	@Operation(summary = "Get Book Item Status",
			description = "This endpoint will return all book item status | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/book-items/status")
	public Set<BookItemStatus> getAllBookItemStatus() {
		return service.findAllBookItemStatus();
	}
	
	@Operation(summary = "Create new Book Item",
			description = "This endpoint will create new book item with the given information | (Authority) ADMIN, MANAGER.")
	@PostMapping("/admin/book-items")
	public ResponseEntity<?> saveBookItem(@Valid @RequestBody BookItemCreateBodyDTO bookItemDTO) {
		var response = new HashMap<String, Object>();
		
		service.saveBookItem(bookItemDTO);
		response.put("message", "Tạo mới tài nguyên sách thành công!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.CREATED.value());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Book Item data",
			description = "This endpoint will update book item with the given information | (Authority) ADMIN, MANAGER.")
	@PutMapping("/admin/book-items")
	public ResponseEntity<?> updateBookItem(@Valid @RequestBody BookItemUpdateBodyDTO bookItemDTO) {
		var response = new HashMap<String, Object>();
		
		service.updateBookItem(bookItemDTO);
		response.put("message", "Cập nhật tài nguyên sách thành công!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Book Item By Id",
			description = "This endpoint will soft delete book item with the given id | (Authority) ADMIN, MANAGER.")
	@DeleteMapping("/admin/book-items/{id}")
	public ResponseEntity<?> deleteBookItemById(@PathVariable("id") Integer id) {
		var response = new HashMap<String, Object>();
		
		service.deleteBookItemById(id);
		response.put("message", "Xóa tài nguyên sách với mã số " + id + " thành công!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoBookItemFoundException.class)
	public ResponseEntity<?> handleNoBookItemFoundException(
			NoBookItemFoundException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookItemExistException.class)
	public ResponseEntity<?> handleNoBookItemExistException(
			NoBookItemExistException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookFoundException.class)
	public ResponseEntity<?> handleNoBookFoundException(
			NoBookFoundException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicatedBookItemException.class)
	public ResponseEntity<?> handleDuplicatedBookItemException(
				DuplicatedBookItemException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
