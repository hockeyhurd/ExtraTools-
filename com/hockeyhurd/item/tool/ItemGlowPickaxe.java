package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemGlowPickaxe extends ItemPickaxe {

	private final Block torch = ExtraTools.glowTorch;
	private TimerHelper th;

	public ItemGlowPickaxe(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("GlowPickaxeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		th = new TimerHelper(20, 2);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowPickaxeUnbreakable");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if (!world.isRemote) {
			Waila waila = new Waila(itemStack, world, entityPlayer, torch, true, false);
			waila.setOffset(1);
			if (!th.getUse() || th.excuser()) waila.finder();
			th.setUse(true);
		}
		return itemStack;
	}

}
