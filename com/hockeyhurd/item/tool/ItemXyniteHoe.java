package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemHoe;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemXyniteHoe extends ItemHoe {

	public ItemXyniteHoe(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("XyniteHoe");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "XyniteHoe");
	}

}
