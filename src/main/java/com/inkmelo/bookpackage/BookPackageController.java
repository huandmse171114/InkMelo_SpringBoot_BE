package com.inkmelo.bookpackage;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPackageController {
	private BookPackageService service;

	public BookPackageController(BookPackageService service) {
		this.service = service;
	}
	
	
}
