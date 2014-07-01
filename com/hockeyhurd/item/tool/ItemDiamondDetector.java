package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.ChunkHelper;
import com.hockeyhurd.util.TimerHelper;

public class ItemDiamondDetector extends Item {

	private boolean inUse = false;
	private TimerHelper th;

	public ItemDiamondDetector(int id) {
		super(id);
		this.setUnlocalizedName("DiamondDetector");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		th = new TimerHelper();
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "DiamondDetector");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!th.getUse()) {
			ChunkHelper chunkHelper = new ChunkHelper(world, player);
			chunkHelper.searchChunk(Block.oreDiamond);
		}
		th.setUse(true);
		return stack;
	}

}
