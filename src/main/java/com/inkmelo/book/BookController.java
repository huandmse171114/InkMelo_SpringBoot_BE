package com.inkmelo.book;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	
	private BookService service;

	public BookController(BookService service) {
		this.service = service;
	}
	
	
}
