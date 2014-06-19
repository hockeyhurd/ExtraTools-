package com.hockeyhurd.item.tool;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class ItemGlowSword extends ItemSword {

	public ItemGlowSword(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowSwordUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowSwordUnbreakable");
	}
	
}
