package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.main.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiamondFusedNetherStar extends Item {

	public ItemDiamondFusedNetherStar(int id) {
		super(id);
		this.setUnlocalizedName("DiamondFusedNetherStar");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister iconReg){
		itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "DiamondNetherStarIngot");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
}

