package com.hockeyhurd.item.tool;

import java.util.List;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
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
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		// list.add("Tooltip stuff goes here");
	}
	
}
