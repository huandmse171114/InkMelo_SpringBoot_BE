package com.inkmelo.genre;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class GenreService {
	private GenreRepository repository;

	public GenreService(GenreRepository repository) {
		this.repository = repository;
	}
	
	public List<GenreResponseDTO> findAllGenre() {
		return repository.findAll()
				.stream()
				.map(genre -> GenreResponseDTO.builder()
						.name(genre.getName())
						.description(genre.getDescription())
						.build())
				.collect(Collectors.toList());
	}
	
}
