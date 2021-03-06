/*
 * Class used to get a world block and return it's name localized or unlocalized based off preference.
 */

package com.hockeyhurd.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.hockeyhurd.util.math.Vector4Helper;

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
	
	public Block getBlock(Vector4Helper<Integer> vec) {
		return getBlock(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public Block getBlockFromID(int id) {
		return (blockListContains(id) ? (Block) Block.blockRegistry.getObjectById(id) : null);
	}
	
	public boolean canMineBlock(int x, int y, int z) {
		return canMineBlock(this.player, x, y, z);
	}
	
	public boolean canMineBlock(Vector4Helper<Integer> vec) {
		return canMineBlock(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public boolean canMineBlock(EntityPlayer player, Vector4Helper<Integer> vec) {
		return canMineBlock(player, vec.getX(), vec.getY(), vec.getZ());
	}
	
	public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
		return world.canMineBlock(player, x, y, z);
	}
	
	public boolean blockExists(int x, int y, int z) {
		return world.blockExists(x, y, z);
	}
	
	public boolean blockExists(Vector4Helper<Integer> vec) {
		return blockExists(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public int getBlockMetaData(int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	public int getBlockMetaData(Vector4Helper<Integer> vec) {
		return getBlockMetaData(vec.getX(), vec.getY(), vec.getZ());
	}
	
	// Returns the block's material
	public Material getBlockMaterial(int x, int y, int z) {
		return world.getBlock(x, y, z).getMaterial();
	}
	
	public Material getBlockMaterial(Vector4Helper<Integer> vec) {
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
	
	public String getUnlocalized(Block block) {
		return block != null ? block.getUnlocalizedName() : "This is not a block!";
	}
	
	public boolean blockListContains(int id) {
		Block block = Block.getBlockById(id);
		return block != null && block != Blocks.air ? true : false;
	}
	
	public boolean blockListContains(Block block) {
		int id = Block.getIdFromBlock(block);
		Block b = Block.getBlockById(id); 
		return b != null && b != Blocks.air ? true : false; 
	}
	
	public void destroyBlock(int x, int y, int z) {
		destroyBlock(x, y, z, true);
	}
	
	public void destroyBlock(int x, int y, int z, boolean drop) {
		world.func_147480_a(x, y, z, drop);
	}
	
	public void destroyBlock(Vector4Helper<Integer> vec, boolean drop) {
		destroyBlock(vec.getX(), vec.getY(), vec.getZ(), drop);
	}
	
	public void destroyBlock(Vector4Helper vec) {
		destroyBlock(vec, true);
	}
	
	public void setBlock(int x, int y, int z, Block block, int metaData) {
		boolean flag = false;
		Block checkBlock = getBlock(x, y, z);
		int tempData = getBlockMetaData(x, y, z);
		if (checkBlock == block && tempData == metaData) flag = true;
		if (!flag) world.setBlock(x, y, z, block, metaData, 3);
		else LogHelper.warn("Couldn't place block:", getUnlocalized(block), "into world at", x, y, z, "with metadata:", metaData);
	}
	
	public void setBlock(int x, int y, int z, Block block) {
		setBlock(x, y, z, block, 0);
	}
	
	public void setBlock(Vector4Helper<Integer> vec, Block block, int metaData) {
		setBlock(vec.getX(), vec.getY(), vec.getZ(), block, metaData);
	}
	
	public void setBlock(Vector4Helper<Integer> vec, Block block) {
		setBlock(vec, block, 0);
	}
	
}
