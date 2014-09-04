package com.hockeyhurd.util.math;

public class TimeLapse {

	private long startTime;
	private static final double ns = 1e6;
	
	public TimeLapse() {
		startTime = System.nanoTime();
	}
	
	public double getEffectiveTimeSince() {
		long currentTime = System.nanoTime();
		double effTime = (currentTime - startTime) / ns;
		return Math.ceil(effTime);
	}

}
