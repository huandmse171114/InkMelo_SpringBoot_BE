package com.inkmelo.bookitem;

import org.springframework.stereotype.Service;

@Service
public class BookItemService {
	private BookItemRepository repository;

	public BookItemService(BookItemRepository repository) {
		this.repository = repository;
	}
	
}
