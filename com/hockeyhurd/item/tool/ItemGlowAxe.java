package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

import com.hockeyhurd.main.ExtraTools;

public class ItemGlowAxe extends ItemAxe {

	public ItemGlowAxe(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowAxeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowAxeUnbreakable");
	}

}
