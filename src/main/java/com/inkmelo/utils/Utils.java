package com.inkmelo.utils;

import java.sql.Timestamp;
import java.util.Date;

public class Utils {
	
	public static Timestamp getCurrentTimestamp() {
		var date = new Date();
		var timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
}
