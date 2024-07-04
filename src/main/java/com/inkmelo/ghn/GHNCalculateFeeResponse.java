package com.inkmelo.ghn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GHNCalculateFeeResponse {
	private String code;
	private String message;
	private GHNCalculateFeeData data;
}
