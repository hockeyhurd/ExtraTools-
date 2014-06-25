package com.hockeyhurd.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockHelper {

	private World world;
	private EntityPlayer player;
	
	public BlockHelper(World world, EntityPlayer player) {
		this.world = world;
		this.player = player;
	}
	
	public Block getBlock(int x, int y, int z) {
		return Block.blocksList[world.getBlockId(x, y, z)];
	}
	
	public String getLocalized(Block block) {
		return block.getLocalizedName();
	}
	
}
