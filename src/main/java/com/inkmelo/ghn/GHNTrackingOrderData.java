package com.inkmelo.ghn;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GHNTrackingOrderData {
	private Long shop_id;
	private String order_code;
	private Timestamp pickup_time;
	private String status;
	private List<String> tag;
}
