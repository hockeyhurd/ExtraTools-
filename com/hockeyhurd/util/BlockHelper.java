/*
 * Class used to get a world block and return it's name localized or unlocalized based off preference.
 */

package com.hockeyhurd.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
	
	// Returns the block's material
	public Material getBlockMaterial(int x, int y, int z) {
		return world.getBlockMaterial(x, y, z);
	}
	
	public String getLocalized(Block block) {
		return block.getLocalizedName();
	}
	
	public String getUnlocalizedName(Block block) {
		return block.getUnlocalizedName();
	}
	
	public boolean blockListContains(int id) {
		Block b = Block.blocksList[Block.blocksList.length - 1];
		
		// Checks if the given id is > the last registered block and if so, just return false; 
		if (b != null && id > b.blockID) return false;
		Block block = null;
		
		for (int i = 0; i < Block.blocksList.length; i++) {
			if (Block.blocksList[i] != null && Block.blocksList[i].blockID == id) {
				block = Block.blocksList[i];
				break;
			}
		}
		
		return block != null ? true : false;
	}
	
}
