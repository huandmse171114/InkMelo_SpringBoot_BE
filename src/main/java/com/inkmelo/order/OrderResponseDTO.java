package com.inkmelo.order;

import java.sql.Date;
import java.util.List;
import com.inkmelo.orderdetail.OrderDetailResponseDTO;

import lombok.Builder;

@Builder
public record OrderResponseDTO(
			Integer id,
			float orderPrice,
			float shippingFee,
			float totalPrice,
			List<OrderDetailResponseDTO> detail,
			Date deliveredDate,
			Date createdAt
			
		) {

}
