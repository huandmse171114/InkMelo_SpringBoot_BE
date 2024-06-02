package com.inkmelo.genre;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GenreService {
	private final GenreRepository repository;
	private final GenreMappingService mappingService;
	
	public GenreService(GenreRepository repository, GenreMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public List<GenreResponseDTO> findAllGenreByStatus(GenreStatus status) {
		return repository.findAllByStatus(status)
				.stream()
				.map(genre -> mappingService.genreToGenreResponseDTO(genre))
				.toList();
	}

	public List<GenreAdminResponseDTO> findAllGenre() {
		return repository.findAll()
				.stream()
				.map(genre -> mappingService.genreToGenreAdminResponseDTO(genre))
				.toList();
	}
	
}
