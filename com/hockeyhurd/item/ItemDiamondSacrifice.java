package com.hockeyhurd.item;

import com.hockeyhurd.mod.ExtraTools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemDiamondSacrifice extends Item {

	public ItemDiamondSacrifice() {
		super();
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setUnlocalizedName("BlackDiamond");
		this.setMaxStackSize(1);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "BlackDiamond");
	}
	
}