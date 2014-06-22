package com.hockeyhurd.item;

import java.util.List;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGlowCoal extends Item {

	public ItemGlowCoal(int id) {
		super(id);
		this.setUnlocalizedName("GlowCoal");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowCoal");
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Smelts up to 12 blocks!");
	}
	
}
