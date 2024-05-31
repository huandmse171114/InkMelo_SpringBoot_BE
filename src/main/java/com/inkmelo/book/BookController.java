package com.inkmelo.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class BookController {
	
	private BookService service;

	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping("/books")
	public List<BookResponseDTO> getAllBooks() {
		return service.findAllBooks();
	}
	
	@GetMapping("/books/search/{keyword}")
	public List<BookResponseDTO> searchBooks(@PathVariable("keyword") String keywordRequest) {
        return service.findBooksByKeyword(keywordRequest);
    }
	
	
}
