/*
 * Class used to get a world block and return it's name localized or unlocalized based off preference.
 */

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
	
	// Returns the block from world coordinate.
	public Block getBlock(int x, int y, int z) {
		return Block.blocksList[world.getBlockId(x, y, z)];  
	}
	
	public String getLocalized(Block block) {
		return block.getLocalizedName();
	}
	
	public String getUnlocalizedName(Block block) {
		return block.getUnlocalizedName();
	}
	
}
