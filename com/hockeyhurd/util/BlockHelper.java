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
	
	// Only use this constructor is there is no need with world or player interaction! i.e. block parameters.
	@Deprecated
	public BlockHelper() {
		
	}
	
	// Returns the block from world coordinate.
	public Block getBlock(int x, int y, int z) {
		return blockExists(x, y, z) ? Block.blocksList[getBlockId(x, y, z)] : null;  
	}
	
	public Block getBlock(int id) {
		return id > 0 && id < Block.blocksList.length ? Block.blocksList[id] : null;
	}
	
	public int getBlockId(Block block) {
		return block.blockID;
	}
	
	public int getBlockId(int x, int y, int z) {
		return world.getBlockId(x, y, z);
	}
	
	public boolean blockExists(int x, int y, int z) {
		return world.blockExists(x, y, z);
	}
	
	public int getBlockMetaData(int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	// Returns the block's material
	public Material getBlockMaterial(int x, int y, int z) {
		return world.getBlockMaterial(x, y, z);
	}
	
	// Set to depreciated until further tested, however is likely it works.
	@Deprecated
	public Material getBlockMaterial(Block block) {
		return block.blockMaterial;
	}
	
	public String getLocalized(Block block) {
		return block != null ? block.getLocalizedName() : "This is not a block!";
	}
	
	public String getUnlocalizedName(Block block) {
		return block != null ? block.getUnlocalizedName() : "This is not a block!";
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
	
	public boolean isABlock(Block block) {
		return blockListContains(block.blockID);
	}
	
}
