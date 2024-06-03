package com.inkmelo.bookpackage;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book Package", description = "Book Package Management APIs")
@RestController
public class BookPackageController {
	private BookPackageService service;

	public BookPackageController(BookPackageService service) {
		this.service = service;
	}
	
	
}
