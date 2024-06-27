package com.inkmelo.customer;

import java.sql.Date;

import lombok.Builder;

@Builder
public record CustomerProfileResponseDTO(
			String fullname,
			Date dateOfBirth,
			CustomerGender gender,
			String phone,
			String email,
			String profileImg,
			Date lastUpdatedTime
		) {

}
