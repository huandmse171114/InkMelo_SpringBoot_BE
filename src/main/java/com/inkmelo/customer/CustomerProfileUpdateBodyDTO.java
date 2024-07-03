package com.inkmelo.customer;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CustomerProfileUpdateBodyDTO(
			@NotEmpty(message = "Họ và tên không được để trống")
			String fullname,
			Date dateOfBirth,
			CustomerGender gender,
			String phone,
			@NotEmpty(message = "Email không được để trống")
			@Email(message = "Vui lòng nhập đúng cú pháp của email")
			String email,
			String profileImg
		) {

}
