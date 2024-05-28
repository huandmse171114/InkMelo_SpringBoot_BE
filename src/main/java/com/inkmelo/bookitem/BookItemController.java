package com.inkmelo.bookitem;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookItemController {
	private BookItemService service;

	public BookItemController(BookItemService service) {
		this.service = service;
	}
	
	
}
