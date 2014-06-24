package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

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
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
	}

}
