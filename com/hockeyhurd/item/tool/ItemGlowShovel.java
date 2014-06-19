package com.hockeyhurd.item.tool;

import java.util.List;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemGlowShovel extends ItemSpade {

	public ItemGlowShovel(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowShovelUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowShovelUnbreakable");
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		// list.add("Tooltip stuff goes here");
	}
	
}
