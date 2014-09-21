package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.extratools.ExtraTools;

public class ItemDiamondSacrifice extends Item {

	public ItemDiamondSacrifice() {
		super();
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setUnlocalizedName("BlackDiamond");
		this.setMaxStackSize(1);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "BlackDiamond");
	}
	
}
