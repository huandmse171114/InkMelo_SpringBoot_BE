package com.inkmelo.genre;

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

import com.inkmelo.exception.NoGenreExistException;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Genre", description = "Genre Management APIs")
@RestController
public class GenreController {
	private GenreService service;

	public GenreController(GenreService service) {
		this.service = service;
	}
	
	@Operation(summary = "Get All Active Genres",
			description = "This endpoint will return all genres that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/store/api/v1/genres")
	public List<GenreResponseDTO> getAllActiveGenres() {
		return service.findAllGenreByStatus(GenreStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Genres",
			description = "This endpoint will return all genres in DB | (Authority) ADMIN.")
	@GetMapping("/admin/api/v1/genres")
	public List<GenreAdminResponseDTO> getAllGenres() {
		return service.findAllGenre();
	}
	
	@Operation(summary = "Get All Genre's Status",
			description = "This endpoint will return all genre's status | (Authority) ADMIN.")
	@GetMapping("/admin/api/v1/genres/status")
	public Set<GenreStatus> getAllGenreStatus() {
		return service.findAllGenreStatus();
	}
	
	@Operation(summary = "Create new Genre",
			description = "This endpoint will create new genre with the given information | (Authority) ADMIN.")
	@PostMapping("/admin/api/v1/genres")
	public ResponseEntity<?> saveGenre(@Valid @RequestBody GenreCreateBodyDTO genreDTO) {
		service.saveGenre(genreDTO);
		
		return Utils.generateMessageResponseEntity(
				"Tạo mới thể loại sách thành công!", 
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Genre data",
			description = "This endpoint will update genre with the given information | (Authority) ADMIN.")
	@PutMapping("/admin/api/v1/genres")
	public ResponseEntity<?> updateGenre(@Valid @RequestBody GenreUpdateBodyDTO genreDTO) {
		service.updateGenre(genreDTO);
		
		return Utils.generateMessageResponseEntity(
				"Cập nhật thể loại sách thành công!", 
				HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Genre By Id",
			description = "This endpoint will soft delete genre with the given id | (Authority) ADMIN.")
	@DeleteMapping("/admin/api/v1/genres/{id}")
	public ResponseEntity<?> deleteGenreById(@PathVariable("id") Integer id){
		service.deleteGenreById(id);
		
		return Utils.generateMessageResponseEntity(
				"Xóa thể loại sách với mã số " + id + " thành công!", 
				HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoGenreFoundException.class)
	public ResponseEntity<?> handleNoGenreFoundException1(
				NoGenreFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoGenreExistException.class)
	public ResponseEntity<?> handleNoGenreExistException(
				NoGenreExistException ex
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
