package com.inkmelo.shipment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ShipmentUpdateBodyDTO(
			@NotNull(message = "Vui lòng không để trống mã địa chỉ vận chuyển.")
			Integer id,
			@NotEmpty(message = "Vui lòng không để trống tên người nhận.")
			String receiverName,
			@NotEmpty(message = "Vui lòng không để trống số điện thoại liên hệ.")
			String contactNumber,
			String description,
			@NotEmpty(message = "Vui lòng không để trống địa chỉ nhà và tên đường.")
			String street,
			@NotEmpty(message = "Vui lòng không để trống thông tin phường/xã.")
			String ward,
			@NotNull
			Integer wardId,
			@NotEmpty(message = "Vui lòng không để trống thông tin quận/huyện.")
			String district,
			@NotNull
			Integer districtId,
			@NotEmpty(message = "Vui lòng không để trống thông tin tỉnh.")
			String province,
			@NotNull
			Integer provinceId,
			boolean isDefault,
			ShipmentStatus status
		) {

}
