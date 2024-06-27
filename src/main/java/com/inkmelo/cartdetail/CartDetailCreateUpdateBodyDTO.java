package com.inkmelo.cartdetail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailCreateUpdateBodyDTO {
	
	@NotNull(message = "Không được để trống mã gói tài nguyên sách")
	private Integer bookPackageId;
	
	@NotNull(message = "Không được để trống số lượng sản phẩm")
	private Integer quantity;

}
