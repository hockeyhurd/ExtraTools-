package com.hockeyhurd.item;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemGlowIngot extends Item {

	public ItemGlowIngot(int id) {
		super(id);
		this.setUnlocalizedName("GlowIngot");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowIngot");
	}

}
