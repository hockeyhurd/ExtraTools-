package com.hockeyhurd.util;

public class LogicHelper {
	
	public LogicHelper() {
	}
	
	/**
	 * @param text = input
	 * @return true if string isn't null, false if it is.
	 */
	public static boolean nullCheck(String text) {
		return text != null && !text.equals("");
	}

	/**
	 * @param flag = input boolean
	 * @return inverse of inputted boolean.
	 */
	public static boolean flipper(boolean flag) {
		return flag ? false : true;
	}

}
