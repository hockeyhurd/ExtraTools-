package com.hockeyhurd.util;

import org.apache.logging.log4j.Level;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {

	public LogHelper() {
	}
	
	public static void log(Level logLevel, Object object) {
		FMLLog.log(ExtraTools.modID, logLevel, String.valueOf(object));
	}

	public static void log(Level level, Object... objects) {
		String text = "";
		for (Object obj : objects) {
			text += String.valueOf(obj) + " ";
		}
		
		log(level, text);
	}
	
	public static void info(Object object) {
		log(Level.INFO, object);
	}
	
	
	public static void info(Object... objects) {
		log(Level.INFO, objects);
	}
	
	public static void warn(Object object) {
		log(Level.WARN, object);
	}
	
	public static void warn(Object... objects) {
		log(Level.WARN, objects);
	}
	
	public static void severe(Object object) {
		log(Level.ERROR, object);
	}
	
	public static void severe(Object... objects) {
		log(Level.ERROR, objects);
	}

}
