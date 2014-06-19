package com.hockeyhurd.item.tool;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class GlowShovelUnbreakble extends ItemSpade {

	public GlowShovelUnbreakble(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowShovelUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowShovelUnbreakable");
	}
	
}
