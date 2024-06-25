package com.inkmelo.auth;

import java.util.List;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
			String username,
			String jwtToken,
			List<String> roles
		) {

}
