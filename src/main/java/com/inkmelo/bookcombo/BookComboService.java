package com.inkmelo.bookcombo;

import org.springframework.stereotype.Service;

@Service
public class BookComboService {
	
	private BookComboRepository repository;

	public BookComboService(BookComboRepository repository) {
		this.repository = repository;
	}
	
	
}
