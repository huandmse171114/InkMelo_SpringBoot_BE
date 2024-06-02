package com.inkmelo.customer;

import lombok.Builder;

@Builder
public record CustomerRatingResponseDTO(
			String fullname,
			String profileImg
		) {

}
