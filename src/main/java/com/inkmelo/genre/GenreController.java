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
	public List<GenreResponseDTO> getAllActiveGenres() {
		return service.findAllGenreByStatus(GenreStatus.ACTIVE);
	}
	
	@GetMapping("/admin/genres")
	public List<GenreAdminResponseDTO> getAllGenres() {
		return service.findAllGenre();
	}
}
