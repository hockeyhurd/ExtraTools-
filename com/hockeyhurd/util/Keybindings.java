package com.hockeyhurd.util;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class Keybindings {

	public static final String[] desc = {
		"key.toggle.desc"
	};
	public static final int[] keyValues = {
		Keyboard.KEY_Y
	};
	public static KeyBinding[] keys;

	public Keybindings() {
	}
	
	public static void init() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; i++) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], "key.extratools+.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
	
}
