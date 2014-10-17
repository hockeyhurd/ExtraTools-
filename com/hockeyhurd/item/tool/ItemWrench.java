package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemWrench extends AbstractToolWrench {

	public ItemWrench() {
		super();
		this.setUnlocalizedName("Wrench");
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "Wrench");
	}

}
