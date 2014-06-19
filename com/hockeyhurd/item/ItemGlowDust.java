package com.hockeyhurd.item;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemGlowDust extends Item {

	public ItemGlowDust(int id) {
		super(id);
		this.setUnlocalizedName("GlowDust");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowDust");
	}

}
