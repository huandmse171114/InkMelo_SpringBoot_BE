package com.inkmelo.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
}
