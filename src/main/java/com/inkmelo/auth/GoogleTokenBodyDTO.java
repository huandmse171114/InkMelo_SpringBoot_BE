package com.inkmelo.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record GoogleTokenBodyDTO(
			@NotEmpty
			String credential,
			@NotEmpty
			String clientId,
			String select_by
		) {

}
