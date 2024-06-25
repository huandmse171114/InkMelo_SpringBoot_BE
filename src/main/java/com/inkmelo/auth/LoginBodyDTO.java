package com.inkmelo.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LoginBodyDTO(
			@NotEmpty
			String username,
			@NotEmpty
			String password
		) {

}
