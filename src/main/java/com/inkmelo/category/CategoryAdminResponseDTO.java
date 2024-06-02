package com.inkmelo.category;

import lombok.Builder;
import java.sql.Date;

@Builder
public record CategoryAdminResponseDTO(
			Integer id,
			String name,
			String description,
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy,
			CategoryStatus status
		) {

}
