package com.hockeyhurd.handler;

import cpw.mods.fml.common.Loader;

public class ModsLoadedHelper {

	public static boolean ic2Loaded;
	
	public ModsLoadedHelper() {
	}

	public static void init() {
		if (Loader.isModLoaded("IC2")) ic2Loaded = true;
	}
	
}
