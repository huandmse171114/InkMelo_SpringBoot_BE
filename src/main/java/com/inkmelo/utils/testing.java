package com.inkmelo.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import com.inkmelo.ghn.GHNApis;


public class testing {
	public static void main(String[] args) {
		GHNApis ghnApi = new GHNApis();
		
		Date expectedDeliveryDate = ghnApi.calculateExpectedDeliveryTime(1750, "511110");
		
		Period period = Period.between(LocalDate.of(expectedDeliveryDate.getYear(), expectedDeliveryDate.getMonth(), expectedDeliveryDate.getDay()), LocalDate.now());
	
		System.out.println(period.getDays());
	}
}
