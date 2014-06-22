package com.hockeyhurd.handler;

public class DefaultIDHandler {

	private int count = 1800;
	
	public DefaultIDHandler() {}
	
	public int getNextAvailableID() {
		return count++;
	}
	
	public int getLength() {
		return count;
	}
}
