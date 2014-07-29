package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.util.ChunkHelper;
import com.hockeyhurd.util.TimerHelper;

public abstract class AbstractToolDetector extends Item {

	protected Block blockToFind;
	protected TimerHelper th;
	protected boolean inUse = false;

	public AbstractToolDetector(Block blockToFind) {
		super();
		init();
		this.blockToFind = blockToFind;
	}
	
	// Method to use to ensure some object classes are initialized.
	protected void init() {
		th = new TimerHelper();
	}

	public abstract void registerIcons(IIconRegister reg);

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!th.getUse()) {
			ChunkHelper chunkHelper = new ChunkHelper(world, player);
			chunkHelper.searchChunk(this.blockToFind);
		}
		th.setUse(true);
		return stack;
	}

}
