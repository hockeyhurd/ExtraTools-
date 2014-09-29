package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemXynitePickaxe extends ItemPickaxe {

	public ItemXynitePickaxe(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("XynitePickaxe");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHarvestLevel(this.toString(), 3);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)  {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "XynitePickaxe");
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Equal to \'Glow\' tools but breakable.");
	}

}
