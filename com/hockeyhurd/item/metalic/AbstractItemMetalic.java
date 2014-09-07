package com.hockeyhurd.item.metalic;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AbstractItemMetalic extends Item {

	protected String fileName;
	
	public AbstractItemMetalic(String name) {
		super();
		this.setUnlocalizedName(name);
		this.fileName = ExtraTools.assetsDir + name;
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(this.fileName);
	}

}
