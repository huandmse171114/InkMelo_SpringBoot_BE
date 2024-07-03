package com.inkmelo.ghn;

import java.util.List;

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
public class GHNServiceResponse {
	private String code;
	private String code_message_value;
	private List<GHNServiceResponseData> data;
	private String message;
}
