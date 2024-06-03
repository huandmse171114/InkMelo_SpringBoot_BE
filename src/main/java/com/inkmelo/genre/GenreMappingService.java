package com.inkmelo.genre;

import org.springframework.stereotype.Service;

@Service
public class GenreMappingService {
	
	public GenreResponseDTO genreToGenreResponseDTO(Genre genre) {
		return GenreResponseDTO.builder()
				.id(genre.getId())
				.name(genre.getName())
				.description(genre.getDescription())
				.build();
	}
	
	public GenreAdminResponseDTO genreToGenreAdminResponseDTO(Genre genre) {
		return GenreAdminResponseDTO.builder()
				.id(genre.getId())
				.name(genre.getName())
				.description(genre.getDescription())
				.createdAt(genre.getCreatedAt())
				.lastChangedBy(genre.getLastChangedBy())
				.lastUpdatedTime(genre.getLastUpdatedTime())
				.build();
	}
	
}
