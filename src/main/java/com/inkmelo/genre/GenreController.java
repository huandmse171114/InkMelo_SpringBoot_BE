package com.inkmelo.genre;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {
	private GenreService service;

	public GenreController(GenreService service) {
		this.service = service;
	}
	
	
}
