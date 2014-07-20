package com.hockeyhurd.handler;

import net.minecraft.item.ItemStack;

import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	private static final int coalBurnTime = 1600;
	
	public FuelHandler() {}
	
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() == ExtraTools.glowCoal) return getTime(10);
		return 0;
	}
	
	private int getTime(int numBlocks) {
		int time = coalBurnTime / 8 * numBlocks;
		return time;
	}

}
