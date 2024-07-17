package com.inkmelo.order;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import com.inkmelo.orderdetail.OrderDetailResponseDTO;

import lombok.Builder;

@Builder
public record OrderResponseDTO(
			Integer id,
			String ghnOrderCode,
			float orderPrice,
			float shippingFee,
			float totalPrice,
			List<OrderDetailResponseDTO> detail,
			Date deliveredDate,
			Timestamp pickupTime,
			List<String> tag,
			Date createdAt,
			String ghnOrderStatus
			
		) {

}
