package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

public class ItemGlowIngot extends Item {

	public ItemGlowIngot() {
		super();
		this.setUnlocalizedName("GlowIngot");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowIngot");
	}

}
