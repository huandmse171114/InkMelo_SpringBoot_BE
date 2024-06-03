package com.inkmelo.publisher;

import java.sql.Date;

import lombok.Builder;

@Builder
public record PublisherAdminResponseDTO(
			String name,
			String description,
			String logoImg,
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy
		) {

}
