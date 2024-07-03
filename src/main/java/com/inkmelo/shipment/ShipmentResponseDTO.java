package com.inkmelo.shipment;

import lombok.Builder;

@Builder
public record ShipmentResponseDTO(
			Integer id,
			String receiverName,
			String contactNumber,
			String description,
			String street,
			String ward,
			String wardCode,
			String district,
			Integer districtId,
			String province,
			Integer provinceId,
			boolean isDefault
		) {

}
