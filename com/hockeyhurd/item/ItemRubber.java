package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRubber extends Item {

	public ItemRubber() {
		super();
		this.setUnlocalizedName("Rubber");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "Rubber");
	}

}
