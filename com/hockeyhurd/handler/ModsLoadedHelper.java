package com.hockeyhurd.handler;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import cpw.mods.fml.common.Loader;

public class ModsLoadedHelper {

	public static boolean ic2Loaded = false;
	public static boolean te4Loaded = false;
	
	private static HashMap<String, Boolean> mapping;
	
	public ModsLoadedHelper() {
	}

	public static void init() {
		if (Loader.isModLoaded("IC2")) ic2Loaded = true;
		if (Loader.isModLoaded("ThermalExpansion")) te4Loaded = true;
		
		initMapping();
	}
	
	private static void initMapping() {
		mapping = new HashMap<String, Boolean>();
		
		mapping.put("ic2", ic2Loaded);
		mapping.put("Thermal Expansion", te4Loaded);
	}
	
	public static final Set<Entry<String, Boolean>> getEntries() {
		return mapping.entrySet();
	}
	
}
