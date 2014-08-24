package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

public class ItemGlowDust extends Item {

	public ItemGlowDust() {
		super();
		this.setUnlocalizedName("GlowDust");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowDust");
	}

}
