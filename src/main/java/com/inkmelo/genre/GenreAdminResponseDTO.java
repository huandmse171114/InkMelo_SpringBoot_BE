package com.inkmelo.genre;

import java.sql.Date;

import lombok.Builder;

@Builder
public record GenreAdminResponseDTO(
			Integer id,
			String name,
			String description,
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy,
			GenreStatus status
		) {

}
