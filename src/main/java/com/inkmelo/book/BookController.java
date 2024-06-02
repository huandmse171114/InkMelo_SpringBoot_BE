package com.inkmelo.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	
	private BookService service;

	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping("/books")
	public List<BookResponseDTO> getAllActiveBooks() {
		return service.findAllBookByStatus(BookStatus.ACTIVE);
	}
	
	@GetMapping("/admin/books")
	public List<BookAdminResponseDTO> getAllBook() {
		return service.findAllBook();
	}
	
	@GetMapping("/books/{keyword}")
	public List<BookResponseDTO> findBookByKeyword(@PathVariable("keyword") String keyword){
		return service.searchBook(keyword);
	}
	
}
