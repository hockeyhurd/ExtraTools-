package com.hockeyhurd.handler;

public class GuiIDHandler {

	private static int count = 0;
	
	public GuiIDHandler() {
	}
	
	public static int getNextAvailableID() {
		return count++;
	}
	
	public static int getLength() {
		return count;
	}
}
