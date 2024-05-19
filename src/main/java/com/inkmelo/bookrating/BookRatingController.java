package com.inkmelo.bookrating;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRatingController {
	
	private BookRatingService service;

	public BookRatingController(BookRatingService service) {
		this.service = service;
	}
	
	
}
