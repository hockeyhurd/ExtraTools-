package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.ChunkHelper;
import com.hockeyhurd.util.TimerHelper;

public class ItemDiamondDetector extends Item {

	private boolean inUse = false;
	private TimerHelper th;

	public ItemDiamondDetector() {
		super();
		this.setUnlocalizedName("DiamondDetector");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		th = new TimerHelper();
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "DiamondDetector");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!th.getUse()) {
			ChunkHelper chunkHelper = new ChunkHelper(world, player);
			chunkHelper.searchChunk(Blocks.diamond_ore);
		}
		th.setUse(true);
		return stack;
	}

}
