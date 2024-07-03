package com.inkmelo.ghn;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GHNOrderResponseData {
	private String order_code;
	private String sort_code;
	private String trans_type;
	private String ward_encode;
	private String district_encode;
	private GHNOrderResponseDataFee fee;
	private float total_fee;
	private Date expected_delivery_time;
	private String operation_partner;
}
