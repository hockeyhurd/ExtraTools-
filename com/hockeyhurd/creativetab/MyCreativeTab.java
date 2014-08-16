package com.hockeyhurd.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

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
	
	public boolean hasSearchBar() {
		return false;
	}
	
}
