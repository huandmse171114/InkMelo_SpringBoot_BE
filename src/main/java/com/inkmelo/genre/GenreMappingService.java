package com.inkmelo.genre;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
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
				.status(genre.getStatus())
				.build();
	}
 
	public Genre genreCreateBodyDTOToGenre(GenreCreateBodyDTO genreDTO) {
		return Genre.builder()
				.name(genreDTO.name())
				.description(genreDTO.description())
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy(SecurityContextHolder.getContext()
						.getAuthentication().getName())
				.status(GenreStatus.ACTIVE)
				.build();
	}
	
}
