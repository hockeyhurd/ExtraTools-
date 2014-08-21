package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.mod.ExtraTools;

public class ItemGlowShovel extends ItemSpade {

	public ItemGlowShovel(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("GlowShovelUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowShovelUnbreakable");
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Unbreakable!");
	}
	
}
