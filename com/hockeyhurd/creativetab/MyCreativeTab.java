package com.hockeyhurd.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MyCreativeTab extends CreativeTabs {

	public MyCreativeTab(int par1, String par2) {
		super(par1, par2);
	}
	
	public int getTabIconItemIndex() {
		// return ExtraTools.glowSword.itemID;
		return Item.diamond.itemID;
	}
	
	public String getTranslatedTabLabel() {
		return "ExtraTools+";
	}
	
}
