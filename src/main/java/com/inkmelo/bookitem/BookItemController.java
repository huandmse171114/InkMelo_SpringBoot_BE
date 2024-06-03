package com.inkmelo.bookitem;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book Item", description = "Book Item Management APIs")
@RestController
public class BookItemController {
	private BookItemService service;

	public BookItemController(BookItemService service) {
		this.service = service;
	}
	
	
}
