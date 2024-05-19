package com.inkmelo.genre;

import org.springframework.stereotype.Service;

@Service
public class GenreService {
	private GenreRepository repository;

	public GenreService(GenreRepository repository) {
		this.repository = repository;
	}
	
	
}
