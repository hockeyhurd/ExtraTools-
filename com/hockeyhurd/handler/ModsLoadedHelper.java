package com.hockeyhurd.handler;

import cpw.mods.fml.common.Loader;

public class ModsLoadedHelper {

	public static boolean ic2Loaded = false;
	public static boolean te4Loaded = false;
	
	public ModsLoadedHelper() {
	}

	public static void init() {
		if (Loader.isModLoaded("IC2")) ic2Loaded = true;
		if (Loader.isModLoaded("ThermalExpansion")) te4Loaded = true;
	}
	
}
