package com.hockeyhurd.item.pulverized;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPulverizedIron extends Item {

	public ItemPulverizedIron() {
		super();
		this.setUnlocalizedName("PulverizedIron");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "PulverizedIron");
	}

}
