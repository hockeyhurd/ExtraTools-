package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.Waila;

public class ItemGlowPickaxe extends ItemPickaxe {

	private final Block torch = ExtraTools.glowTorch;
	private final int torchID = torch.blockID;
	
	public ItemGlowPickaxe(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowPickaxeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowPickaxeUnbreakable");
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		new Waila(itemStack, world, entityPlayer, torch, true, false).getBlockLookingAt();
		return itemStack;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Tooltip stuff goes here");
	}

}
