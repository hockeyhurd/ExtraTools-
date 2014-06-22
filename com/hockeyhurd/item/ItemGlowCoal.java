package com.hockeyhurd.item;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemGlowCoal extends Item {

	public ItemGlowCoal(int id) {
		super(id);
		this.setUnlocalizedName("GlowCoal");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowCoal");
	}
	
}
