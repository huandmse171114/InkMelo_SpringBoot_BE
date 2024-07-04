package com.inkmelo.order;

import java.sql.Date;
import java.util.List;

import com.inkmelo.cartdetail.CartDetailResponseDTO;

import lombok.Builder;

@Builder
public record OrderCreateBodyDTO(
			float totalPrice,
			float shippingFee,
			Integer quantity,
			Integer shipmentId,
			List<Integer> items,
			Integer serviceId,
			String redirectUrl
		) {

}
