package com.inkmelo.ghn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GHNOrderResponseDataFee {
	private Long main_service;
	private int insurance;
	private int cod_fee;
	private int station_do;
	private int station_pu;
	private int r2s;
	
	
}
