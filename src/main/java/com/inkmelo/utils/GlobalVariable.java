package com.inkmelo.utils;

public class GlobalVariable {
	private static String redirectURL = "";

	public static String getRedirectURL() {
		return redirectURL;
	}

	public static void setRedirectURL(String redirectURL) {
		GlobalVariable.redirectURL = redirectURL;
	}
	
	
}
