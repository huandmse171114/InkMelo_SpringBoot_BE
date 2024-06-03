package com.inkmelo.genre;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Genre", description = "Genre Management APIs")
@RestController
public class GenreController {
	private GenreService service;

	public GenreController(GenreService service) {
		this.service = service;
	}
	
	@Operation(summary = "Get All Active Genres",
			description = "This endpoint will return all genres that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/genres")
	public List<GenreResponseDTO> getAllActiveGenres() {
		return service.findAllGenreByStatus(GenreStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Genres",
			description = "This endpoint will return all genres in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/genres")
	public List<GenreAdminResponseDTO> getAllGenres() {
		return service.findAllGenre();
	}
	
	
//	====================================== Exception Handler ===================================
	
	
	
}
