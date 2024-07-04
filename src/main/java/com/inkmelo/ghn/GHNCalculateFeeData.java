package com.inkmelo.ghn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GHNCalculateFeeData {
	private Long total;
	private Long service_fee;
	private Long insurance_fee;
	private Long pick_station_fee;
	private Long coupon_value;
	private Long r2s_fee;
	private int return_again;
	private int document_return;
	private int double_check;
	private Long cod_fee;
	private Long pick_remote_areas_fee;
	private Long deliver_remote_areas_fee;
	private Long cod_failed_fee;
}
