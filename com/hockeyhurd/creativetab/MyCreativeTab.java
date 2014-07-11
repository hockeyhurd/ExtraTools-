package com.hockeyhurd.creativetab;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MyCreativeTab extends CreativeTabs {

	public MyCreativeTab(int par1, String par2) {
		super(par1, par2);
	}
	
	public int getTabIconItemIndex() {
		// return Item.diamond.itemID;
		return ExtraTools.fireryNetherStar.itemID;
	}
	
	public String getTranslatedTabLabel() {
		return "ExtraTools+";
	}
	
}
