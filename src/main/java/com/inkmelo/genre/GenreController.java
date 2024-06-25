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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoGenreExistException;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.utils.MessageResponseDTO;
import com.inkmelo.utils.PagingListResposneDTO;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Genre", description = "Genre Management APIs")
@RestController
@RequiredArgsConstructor
public class GenreController {
	private final GenreService service;
	
	@Operation(summary = "Get Active Genres Only",
			description = "This endpoint will return genres that have ACTIVE status in DB with paging option and search by genre's name | (Authority) ALL.")
	@ApiResponse(responseCode = "200", description = "Found the Genres, response with paging",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = PagingListResposneDTO.class))),
	})
	@ApiResponse(responseCode = "400", description = "Bad Request Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@ApiResponse(responseCode = "404", description = "Not Found Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@GetMapping("/store/api/v1/genres")
	public ResponseEntity<?> getAllActiveGenres(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false, name = "query") String keyword
			) {
		
		if (keyword == null) keyword = "";
		
		return service.findAllGenreByStatus(GenreStatus.ACTIVE, page, size, keyword);
	}
	
	@Operation(summary = "Get Genres",
			description = "This endpoint will return all genres in DB, with paging option and search by genre's name | (Authority) ADMIN.")
	@ApiResponse(responseCode = "200", description = "Found the Genres, response with paging",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = PagingListResposneDTO.class))),
	})
	@ApiResponse(responseCode = "400", description = "Bad Request Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@ApiResponse(responseCode = "404", description = "Not Found Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@GetMapping("/admin/api/v1/genres")
	public ResponseEntity<?> getAllGenres(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false, name = "query") String keyword
			) {
		
		if (keyword == null) keyword = "";
		
		return service.findAllGenre(page, size, keyword);
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
