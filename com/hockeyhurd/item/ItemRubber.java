package com.hockeyhurd.item;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemRubber extends Item {

	public ItemRubber(int id) {
		super(id);
		this.setUnlocalizedName("Rubber");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "Rubber");
	}

}
