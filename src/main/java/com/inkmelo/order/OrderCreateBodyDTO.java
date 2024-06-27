package com.inkmelo.order;

import java.util.List;

import com.inkmelo.cartdetail.CartDetailResponseDTO;

import lombok.Builder;

@Builder
public record OrderCreateBodyDTO(
			float totalPrice,
			String receiverName,
			String contactNumber,
			String shipmentStreet,
			String shipmentWard,
			String shipmentDistrict,
			String shipmentProvince,
			List<CartDetailResponseDTO> items,
			String redirectUrl
		) {

}
