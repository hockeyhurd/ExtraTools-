package com.hockeyhurd.creativetab;

import com.hockeyhurd.mod.ExtraTools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MyCreativeTab extends CreativeTabs {

	public MyCreativeTab(int par1, String par2) {
		super(par1, par2);
	}
	
	public Item getTabIconItem() {
		return ExtraTools.fireryNetherStar;
	}
	
	public String getTranslatedTabLabel() {
		return "ExtraTools+";
	}
	
}
