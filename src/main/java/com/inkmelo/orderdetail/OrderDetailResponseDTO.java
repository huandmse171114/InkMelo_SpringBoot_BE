package com.inkmelo.orderdetail;

import com.inkmelo.bookpackage.BookPackageResponseDTO;

import lombok.Builder;

@Builder
public record OrderDetailResponseDTO(
			Integer id,
			int quantity,
			float itemPrice,
			float totalPrice,
			BookPackageResponseDTO bookPackage
		) {

}
