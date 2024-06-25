package com.inkmelo.user;

import java.sql.Date;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

@Builder
public record UserAdminResponseDTO(
			Integer id,
			String username,
			String fullname,
			String email,
			@Enumerated(EnumType.STRING)
			UserRole role,
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy,
			@Enumerated(EnumType.STRING)
			UserStatus status
			
		) {

}
