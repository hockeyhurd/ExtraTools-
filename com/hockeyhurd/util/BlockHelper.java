/*
 * Class used to get a world block and return it's name localized or unlocalized based off preference.
 */

package com.hockeyhurd.util;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockHelper {

	private World world;
	private EntityPlayer player;
	
	public BlockHelper(World world, EntityPlayer player) {
		this.world = world;
		this.player = player;
	}
	
	// Only use this constructor if there is no need with world or player interaction! i.e. block parameters.
	@Deprecated
	public BlockHelper() {
		
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}
	
	public void setWorldPlayer(World world, EntityPlayer player) {
		setWorld(world);
		setPlayer(player);
	}
	
	public Block getBlock(int id) {
		// return id > 0 && id < Block.blockRegistry. ? Block.blocksList[id] : null;
		return (Block) Block.blockRegistry.getObjectById(id);
	}
	
	public Block getBlock(int x, int y, int z) {
		return world.getBlock(x, y, z);
	}
	
	public Block getBlock(Vector3IHelper vec) {
		return getBlock(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public Block getBlockFromID(int id) {
		return (blockListContains(id) ? (Block) Block.blockRegistry.getObjectById(id) : null);
	}
	
	public boolean canMineBlock(int x, int y, int z) {
		return canMineBlock(this.player, x, y, z);
	}
	
	public boolean canMineBlock(Vector3IHelper vec) {
		return canMineBlock(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public boolean canMineBlock(EntityPlayer player, Vector3IHelper vec) {
		return canMineBlock(player, vec.getX(), vec.getY(), vec.getZ());
	}
	
	public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
		return world.canMineBlock(player, x, y, z);
	}
	
	public boolean blockExists(int x, int y, int z) {
		return world.blockExists(x, y, z);
	}
	
	public boolean blockExists(Vector3IHelper vec) {
		return blockExists(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public int getBlockMetaData(int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	public int getBlockMetaData(Vector3IHelper vec) {
		return getBlockMetaData(vec.getX(), vec.getY(), vec.getZ());
	}
	
	// Returns the block's material
	public Material getBlockMaterial(int x, int y, int z) {
		return world.getBlock(x, y, z).getMaterial();
	}
	
	public Material getBlockMaterial(Vector3IHelper vec) {
		return getBlockMaterial(vec.getX(), vec.getY(), vec.getZ());
	}
	
	// Set to depreciated until further tested, however is likely it works.
	@Deprecated
	public Material getBlockMaterial(Block block) {
		return block.getMaterial();
	}
	
	public String getLocalized(Block block) {
		return block != null ? block.getLocalizedName() : "This is not a block!";
	}
	
	public String getUnlocalizedName(Block block) {
		return block != null ? block.getUnlocalizedName() : "This is not a block!";
	}
	
	public boolean blockListContains(int id) {
		Block b = getBlock(id);
		
		if (b != null && b != Blocks.air) return false;
		Block block = null;
		
		Iterator iter = Block.blockRegistry.iterator();
		while (iter.hasNext()) {
			if (iter.next() instanceof Block) block = (Block) iter.next();
			if (block == b) break;
		}
		
		return block != null && block != Blocks.air ? true : false;
	}
	
	public boolean isABlock(Block block) {
		return block != null && block != Blocks.air && Block.blockRegistry.containsKey(block) ? true : false;
	}
	
	public void destroyBlock(int x, int y, int z) {
		destroyBlock(x, y, z, true);
	}
	
	public void destroyBlock(int x, int y, int z, boolean drop) {
		world.func_147480_a(x, y, z, drop);
	}
	
	public void destroyBlock(Vector3IHelper vec, boolean drop) {
		destroyBlock(vec.getX(), vec.getY(), vec.getZ(), drop);
	}
	
	public void destroyBlock(Vector3IHelper vec) {
		destroyBlock(vec, true);
	}
	
}
