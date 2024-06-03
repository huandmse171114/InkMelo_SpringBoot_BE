package com.inkmelo.bookrating;

import java.sql.Date;

import com.inkmelo.customer.CustomerRatingResponseDTO;

import lombok.Builder;

@Builder
public record BookRatingResponseDTO(
			int star,
			String comment,
			CustomerRatingResponseDTO customer,
			Date createdAt
		) {

}
