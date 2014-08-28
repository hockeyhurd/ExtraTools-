package com.hockeyhurd.item.pulverized;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPulverizedGold extends Item {

	public ItemPulverizedGold() {
		super();
		this.setUnlocalizedName("PulverizedGold");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "PulverizedGold");
	}

}
