package com.inkmelo.ghn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GHNOrderResponse {
	private String code;
	private String code_message_value;
	private GHNOrderResponseData data;
	private String message;
	private String message_display;
}
