package com.inkmelo.bookrating;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book Rating", description = "Book Rating Management APIs")
@RestController
public class BookRatingController {
	
	private BookRatingService service;

	public BookRatingController(BookRatingService service) {
		this.service = service;
	}
	
	
}
