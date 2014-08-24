package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

public class ItemRubber extends Item {

	public ItemRubber() {
		super();
		this.setUnlocalizedName("Rubber");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "Rubber");
	}

}
