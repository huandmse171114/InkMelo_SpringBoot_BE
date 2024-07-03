package com.inkmelo.ghn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GHNServiceResponseData {
	private Integer service_id;
	private String short_name;
	private Integer service_type_id;
	private String config_fee_id;
	private String extra_cost_id;
	private String standard_config_fee_id;
	private String standard_extra_cost_id;
}
