package com.hockeyhurd.util.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Deprecated due to future removal.
@Deprecated
public interface IToolDetector {
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);
}