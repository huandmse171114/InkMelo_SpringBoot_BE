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
public class GHNOrderItem {
	private String name;
	private String code;
	private int quantity;
	private int price;
	private int width;
	private int height;
	private int weight;
	private GHNOrderItemCategory category;
}
