package com.inkmelo.book;

import java.util.List;

import com.inkmelo.genre.GenreResponseDTO;

import lombok.Builder;

@Builder
public record BookOrderResponseDTO(
			Integer id,
			String title,
			String author,
			String bookCoverImg,
			String publisherName,
			List<GenreResponseDTO> genres
		) {

}
