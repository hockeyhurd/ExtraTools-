package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemGlowPickaxe extends ItemPickaxe {

	private final Block torch = ExtraTools.glowTorch;
	private final int torchID = torch.blockID;
	private TimerHelper th;

	public ItemGlowPickaxe(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowPickaxeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		th = new TimerHelper(20, 2);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowPickaxeUnbreakable");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		Waila waila = new Waila(itemStack, world, entityPlayer, torch, true, false);
		waila.setOffset(1);
		if (!th.getUse() || th.excuser()) waila.finder();
		th.setUse(true);
		return itemStack;
	}

	/*
	 * public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
	 * 
	 * }
	 */

}
