package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.mod.ExtraTools;

public class ItemGlowAxe extends ItemAxe {

	public ItemGlowAxe(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("GlowAxeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowAxeUnbreakable");
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Unbreakable!");
	}

}
