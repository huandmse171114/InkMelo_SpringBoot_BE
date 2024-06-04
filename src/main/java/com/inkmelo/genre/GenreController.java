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
	@GetMapping("/genres")
	public List<GenreResponseDTO> getAllActiveGenres() {
		return service.findAllGenreByStatus(GenreStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Genres",
			description = "This endpoint will return all genres in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/genres")
	public List<GenreAdminResponseDTO> getAllGenres() {
		return service.findAllGenre();
	}
	
	@Operation(summary = "Get All Genre's Status",
			description = "This endpoint will return all genre's status | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/genres/status")
	public Set<GenreStatus> getAllGenreStatus() {
		return service.findAllGenreStatus();
	}
	
	@Operation(summary = "Create new Genre",
			description = "This endpoint will create new genre with the given information | (Authority) ADMIN, MANAGER.")
	@PostMapping("/admin/genres")
	public ResponseEntity<?> saveGenre(@Valid @RequestBody GenreCreateBodyDTO genreDTO) {
		var response = new HashMap<String, Object>();
		service.saveGenre(genreDTO);
		response.put("message", "Create new genre successfully!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.CREATED.value());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Genre data",
			description = "This endpoint will update genre with the given information | (Authority) ADMIN, MANAGER.")
	@PutMapping("/admin/genres")
	public ResponseEntity<?> updateGenre(@Valid @RequestBody GenreUpdateBodyDTO genreDTO) {
		var response = new HashMap<String, Object>();
		
		service.updateGenre(genreDTO);
		response.put("message", "Update genre successfully!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Genre By Id",
			description = "This endpoint will soft delete genre with the given id | (Authority) ADMIN, MANAGER")
	@DeleteMapping("/admin/genres/{id}")
	public ResponseEntity<?> deleteGenreById(@PathVariable("id") Integer id){

		var response = new HashMap<String, Object>();
		service.deleteGenreById(id);
		response.put("message", "Delete genre with id " + id + " successfully!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoGenreFoundException.class)
	public ResponseEntity<?> handleNoGenreFoundException1(
				NoGenreFoundException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoGenreExistException.class)
	public ResponseEntity<?> handleNoGenreExistException(
				NoGenreExistException ex
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
