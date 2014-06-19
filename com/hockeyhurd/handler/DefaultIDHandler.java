package com.hockeyhurd.handler;

public class DefaultIDHandler {

	private static int count = 1800;
	
	public DefaultIDHandler() {}
	
	public static int getNextAvailableID() {
		return count++;
	}
}
