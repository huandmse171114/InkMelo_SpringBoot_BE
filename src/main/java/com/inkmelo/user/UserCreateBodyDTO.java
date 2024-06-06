package com.inkmelo.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserCreateBodyDTO(
			@NotEmpty
			String username,
			String fullname,
			@NotEmpty
			@Email
			String email,
			@NotEmpty
			String password,
			@NotEmpty
			String confirmPassword,
			@Enumerated(EnumType.STRING)
			UserRole role
		) {

}
