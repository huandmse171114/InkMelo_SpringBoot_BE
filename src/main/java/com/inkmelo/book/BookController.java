package com.inkmelo.book;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.inkmelo.category.CategoryStatus;
import com.inkmelo.exception.NoBookExistException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.genre.Genre;
import com.inkmelo.genre.GenreService;
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

@Tag(name = "Book", description = "Book Management APIs")
@RestController
public class BookController {
	
	private GenreService genreService;
	
	private BookService service;

	public BookController(BookService service) {
		this.service = service;
	}
	
	@Operation(summary = "Get Active Books Only", 
			description = "This endpoint will return books that have ACTIVE status in DB, with paging option and search by book title or author name | (Authority) ALL")
	@ApiResponse(responseCode = "200", description = "Found the Books, response with paging",
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
	@GetMapping("/store/api/v1/books")
	public ResponseEntity<?> getAllActiveBooks(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false, name = "query") String keyword
			) {
		
		if (keyword == null) keyword = "";
		
		return service.findAllBookByStatus(BookStatus.ACTIVE, page, size, keyword);
	}
	
	@Operation(summary = "Get Books", 
			description = "This endpoint will return books in DB, with paging option and search by book title or author name | (Authority) ADMIN, MANAGER")
	@ApiResponse(responseCode = "200", description = "Found the Books, response with paging",
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
	@GetMapping("/admin/api/v1/books")
	public ResponseEntity<?> getAllBook(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false, name = "query") String keyword
			) {
		
		if (keyword == null) keyword = "";
		
		return service.findAllBook(page, size, keyword);
	}
	
	@GetMapping("/store/api/v1/books/{id}")
	public BookResponseDTO getBookById(@PathVariable("id") Integer id) {
		return service.findBookById(id);
	}
	
	@GetMapping("/admin/api/v1/books/{id}")
	public BookAdminResponseDTO getAdminBookById(@PathVariable("id") Integer id) {
		return service.findAdminBookById(id);
	}
	
	@Operation(summary = "Get All Book's Status",
			description = "This endpoint will return all book's status | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/books/status")
	public Set<BookStatus> getAllBookStatus() {
		return service.findAllBookStatus();
	}
	
//	@Operation(summary = "Search Book By Keyword", 
//			description = "This endpoint will return all books that have the keyword first in name, and then returns all books that have keyword in author name  | (Authority) ALL")
//	@GetMapping("/store/api/v1/books/{keyword}")
//	public List<BookResponseDTO> findBookByKeyword(@PathVariable("keyword") String keyword){
//		return service.searchBook(keyword);
//	}
	
	@Operation(summary = "Create new Book", 
			description = "This endpoint will create new book with the given information  | (Authority) ADMIN, MANAGER")
	@PostMapping("/admin/api/v1/books")
	public ResponseEntity<?> saveBook(@Valid @RequestBody BookCreateBodyDTO bookDTO) {
		service.saveBook(bookDTO);
		
		return Utils.generateMessageResponseEntity(
				"Tạo cuốn sách mới thành công.", 
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Book data", 
			description = "This endpoint will update book with the given information  | (Authority) ADMIN, MANAGER")
	@PutMapping("/admin/api/v1/books")
	public ResponseEntity<?> updateBook(@Valid @RequestBody BookUpdateBodyDTO bookDTO) {
		var response = new HashMap<String, Object>();
		
		service.updateBook(bookDTO);

		return Utils.generateMessageResponseEntity(
				"Cập nhật cuốn sách thành công!", 
				HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Book By Id", 
			description = "This endpoint will soft delete book with the given id | (Authority) ADMIN, MANAGER")
	@DeleteMapping("/admin/api/v1/books/{id}")
	public ResponseEntity<?> deleteBookById(@PathVariable("id") Integer id) {

		service.deleteBookById(id);
		
		return Utils.generateMessageResponseEntity(
				"Xóa cuốn sách với mã số " + id + " thành công!", 
				HttpStatus.OK);
	}
	
	@GetMapping("store/api/v1/books-by-genre/{genreId}")
	public ResponseEntity<Page<ListBookDTO>> getBooksByGenre(
            @PathVariable Integer genreId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
        Page<ListBookDTO> books = service.getBooksByGenre(genreId, page, size);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoBookFoundException.class)
	public ResponseEntity<?> handleNoBookFoundException(
			NoBookFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoBookExistException.class)
	public ResponseEntity<?> handleNoBookExistException(
				NoBookExistException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoGenreFoundException.class)
	public ResponseEntity<?> handleNoGenreFoundException(
			NoGenreFoundException ex
			) {
		
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoPublisherFoundException.class)
	public ResponseEntity<?> handleNoPublisherFoundException(
			NoPublisherFoundException ex
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
