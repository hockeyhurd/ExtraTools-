package com.hockeyhurd.item;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.Waila;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNetherStarFirery extends Item {

	public ItemNetherStarFirery(int id) {
		super(id);
		this.setUnlocalizedName("NetherStarFirery");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "FireryNetherStar");
	}
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
