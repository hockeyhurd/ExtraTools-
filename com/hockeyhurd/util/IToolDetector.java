package com.hockeyhurd.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IToolDetector {
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);
	
}