package com.hockeyhurd.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGlowCoal extends Item {

	public ItemGlowCoal() {
		super();
		this.setUnlocalizedName("GlowCoal");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowCoal");
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Smelts up to 12 blocks!");
	}
	
}
