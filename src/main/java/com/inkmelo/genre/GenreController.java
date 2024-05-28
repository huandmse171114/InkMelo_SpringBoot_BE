package com.inkmelo.genre;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GenreController {
	private GenreService service;

	public GenreController(GenreService service) {
		this.service = service;
	}
	
	@GetMapping("/genres")
	public List<Genre> getAllGenres() {
		return service.findAllGenre();
	}
}
