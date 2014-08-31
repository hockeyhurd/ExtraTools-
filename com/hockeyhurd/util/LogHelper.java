package com.hockeyhurd.util;

import org.apache.logging.log4j.Level;

import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {

	public LogHelper() {
	}
	
	public static void log(Level logLevel, Object object) {
		FMLLog.log(ExtraTools.modID, logLevel, String.valueOf(object));
	}
	
	public static void info(Object object) {
		log(Level.INFO, object);
	}
	
	public static void info(Object... objects) {
		String text = "";
		for (Object obj : objects) {
			text += obj + " ";
		}
		
		info(text);
	}
	
	public static void warn(Object object) {
		log(Level.WARN, object);
	}
	
	public static void severe(Object object) {
		log(Level.ERROR, object);
	}

}
