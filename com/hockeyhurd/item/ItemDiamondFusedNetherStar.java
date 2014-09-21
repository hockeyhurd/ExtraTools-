package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiamondFusedNetherStar extends Item {

	public ItemDiamondFusedNetherStar() {
		super();
		this.setUnlocalizedName("DiamondFusedNetherStar");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister iconReg){
		itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "DiamondNetherStarIngot");
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

