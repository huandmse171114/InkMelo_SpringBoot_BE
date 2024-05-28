package com.inkmelo.bookpackage;

import org.springframework.stereotype.Service;

import com.inkmelo.book.BookRepository;

@Service
public class BookPackageService {
	private BookRepository repository;

	public BookPackageService(BookRepository repository) {
		this.repository = repository;
	}
	
}
