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
	private Long serviceFee;
	private Long insuranceFee;
	private Long pickStationFee;
	private Long couponvalue;
	private Long r2sFee;
	private int returnAgain;
	private int documentReturn;
	private int doubleCheck;
	private Long codFee;
	private Long pickRemoteAreasFee;
	private Long deliverRemoteAreasFee;
	private Long codFailedFee;
}
