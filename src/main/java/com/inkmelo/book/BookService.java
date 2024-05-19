package com.inkmelo.book;

import org.springframework.stereotype.Service;

@Service
public class BookService {

	private BookRepository repository;

	public BookService(BookRepository repository) {
		this.repository = repository;
	}
	
	
	
}
