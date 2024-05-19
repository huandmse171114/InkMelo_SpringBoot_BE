package com.inkmelo.bookcombo;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookComboController {
	
	private BookComboService service;

	public BookComboController(BookComboService service) {
		this.service = service;
	}
	
	
}
