/* Note:
 * 	This class has nothing to do and/or
 * 	is associated with the mod WAILA.
 */

package com.hockeyhurd.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;

public class Waila {

	private ItemStack stack;
	private World world;
	private EntityPlayer player;
	private Block block;
	private BlockHelper bh;
	private boolean placeBlock;
	private boolean shiftClick;
	private List<Block> blockBlackList;

	// Tools/items used.
	private final int pickID;
	private final int hoeID;
	private final int hammerID;

	private int sideHit = 0;
	private int offset;
	private boolean returnBlock = false;

	public Waila(ItemStack itemStack, World world, EntityPlayer entityPlayer, Block block, boolean placeBlock, boolean shiftClick) {
		this.stack = itemStack;
		this.world = world;
		this.player = entityPlayer;
		this.block = block;
		this.bh = new BlockHelper(world, player);
		this.placeBlock = placeBlock; // TODO: implement some sort if placeBlock
										// = false, return block looking at.
		this.shiftClick = shiftClick;

		pickID = new ItemStack(ExtraTools.glowPickaxeUnbreakable, 1).itemID;
		hoeID = new ItemStack(ExtraTools.glowHoeUnbreakable, 1).itemID;
		hammerID = new ItemStack(ExtraTools.glowHammerUnbreakable, 1).itemID;
		blockBlackList = new ArrayList<Block>();
		addBlockBlackList();

		offset = 0; // By default, don't offset anything.
	}

	private void addBlockBlackList() {
		add(ExtraTools.glowTorch);
		add(Block.torchWood);
		add(Block.rail);
		add(Block.railActivator);
		add(Block.railDetector);
		add(Block.railPowered);
		add(Block.bedrock);
		
		for (int i = 0; i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if (block != null && block.blockHardness == -1 && !blockBlackList.contains(block)) add(block);
			else continue;
		}
	}
	
	private void add(Block block) {
		blockBlackList.add(block);
	}

	public void setOffset(int offset) {
		/*
		 * If the setOffsetVal > 0 set this.offSet = offset else, set this.offset = 0, (removing offset to faulty val).
		 */
		this.offset = (offset > 0 ? offset : 0);
	}

	public ItemStack finder() {
		return finder(true);
	}

	// We return stack to avoid any remote possible item damaging.
	public ItemStack finder(boolean handler) {
		if (stack.getItemDamage() >= 0) {
			float f = 1.0F;

			// Get the avgCurrent rotational pitch (left, right)
			float rotPitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;

			// Get the avgCurrent rotational yaw (up, down)
			float rotYaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;

			// Get the avgCurrent position X.
			double posX = player.prevPosX + (player.posX - player.prevPosX) * (double) f;

			// Get the avgCurrent position Y and offset the yCorrd to the
			// player's head level (camera level).
			double posY = (player.prevPosY + (player.posY - player.prevPosY) * (double) f + 1.6200000000000001D) - (double) player.yOffset;

			// Get the avgCurrent position Z.
			double posZ = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;

			// Get the vector represented by the combination of the three above
			// world coordinates.
			Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);

			// Get the -rotYaw and represent deltaDegrees about the y-axis;
			// calculate cos('x').
			float f3 = MathHelper.cos(-rotYaw * 0.01745329F - 3.141593F);

			// Get the -rotYaw and represent the deltaDegrees about the y-axis;
			// calculate sin('x').
			float f4 = MathHelper.sin(-rotYaw * 0.01745329F - 3.141593F);

			// Get the -rotPitch and represent the deltaDegrees about the
			// x-axis; calculate cos('y').
			float f5 = -MathHelper.cos(-rotPitch * 0.01745329F);

			// Get the -rotPitch and represent the deltaDegrees about the
			// x-axis; calculate sin('y').
			float f6 = MathHelper.sin(-rotPitch * 0.01745329F);

			// Get absoulute 'x' value calculated via cos('x') and sin('x').
			float f7 = f4 * f5;
			// Represent null deltaZ.
			float f8 = f6;
			// Get the absolute 'y' value calculated via cos('y') and sin('y').
			float f9 = f3 * f5;
			// Get the distance the vector ray should extend to.
			// double d3 = 5000D;
			double d3 = 5000D; 

			// Get the above calculations and represent this in a vector3
			// format.
			Vec3 vec3d1 = vec3d.addVector((double) f7 * d3, (double) f8 * d3 + 1, (double) f9 * d3);

			/*
			 * Combine vector rotations and vector absolute world positions and throw it through a vector ray to calculate the direction and block the entity (player) is currently looking at in the given instance.
			 */
			MovingObjectPosition movingObjectPos = world.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);

			// Make sure there is no possibility the entity (player) is not
			// looking at 'null'.
			if (movingObjectPos == null) return stack;

			// Check if the vector ray intersects with some sort of TILE
			if (movingObjectPos.typeOfHit == EnumMovingObjectType.TILE) {

				// Get the posiotion of the TILE intersected as represented in
				// 3D space.
				int xx = movingObjectPos.blockX;
				int yy = movingObjectPos.blockY;
				int zz = movingObjectPos.blockZ;

				// Get the side of which the vector ray intersects with.
				int sideHit = movingObjectPos.sideHit;
				// print("Side: " + sideHit);

				if (handler) placeBlockHandler(world, xx, yy, zz, sideHit);
				else setSideHit(sideHit);
			}

			stack.setItemDamage(0);
		}
		return stack;
	}

	private void placeBlockHandler(World world, int xx, int yy, int zz, int sideHit) {
		if (placeBlock) {
			/*
			 * Place block (torch) accordingly to the player perspective. Makes sure the torch is place directly in-front of them. SIDE NOTE: sideHit = 0 means they are looking at the under side of a block and therefore make sure the torch cannot be placed.
			 */
			if (sideHit == 0) return;
			else if (sideHit == 1) setBlock(xx, yy + this.offset, zz);
			else if (sideHit == 2) setBlock(xx, yy, zz - this.offset);
			else if (sideHit == 3) setBlock(xx, yy, zz + this.offset);
			else if (sideHit == 4) setBlock(xx - this.offset, yy, zz);
			else if (sideHit == 5) setBlock(xx + this.offset, yy, zz);

		}

		else if (!placeBlock) {
			// Check if item used == glowHoe
			if ((stack.itemID == hoeID) && sideHit == 1) {

				// Get Block and block ids'
				Block tilDirt = Block.tilledField;
				int dirtID = Block.dirt.blockID;
				int grassID = Block.grass.blockID;
				int tilDirtID = tilDirt.blockID;
				int currentID = world.getBlockId(xx, yy, zz);

				// Get the block the player is currently looking at
				if (currentID == dirtID || currentID == grassID || currentID == tilDirtID) {

					/*
					 * if they shift click, till a 9 * 9 area else, hoe the block they are looking at
					 */
					if (shiftClick) tillLand(xx, yy, zz);
					else world.setBlock(xx, yy, zz, tilDirtID);

					// Play world sound for all to hear :)
					world.playSoundEffect((double) (xx + 0.5), (double) (yy + 0.5), (double) (zz + 0.5), tilDirt.stepSound.getStepSound(), (tilDirt.stepSound.getVolume() + 1.0f) / 2.0f, tilDirt.stepSound.getPitch() * 0.8f);
				}

				/*
				 * If the block the user is looking at cannot be tilled, don't do anything! (yet)
				 */
				else print("Block could not be tilled!");
			}

			else if (stack.itemID == hammerID) mineArea(sideHit, xx, yy, zz);

			// If don't place a block and player is not using a glowHoe and want to return the block being looked at?
			else {
				String blockName = bh.getBlock(xx, yy, zz).getLocalizedName();
				print(blockName);
			}

		}
	}

	private void setSideHit(int sideHit) {
		this.sideHit = sideHit;
	}

	public int getSideHit() {
		return this.sideHit;
	}

	// Method used that can mine a 2-Dimensional area based off a given side hit.
	private void mineArea(int sideHit, int x, int y, int z) {
		/*
		 * sideHit == 0, bottom sideHit == 1, top sideHit == 2, front sideHit == 3, back sideHit == 4, left sideHit == 5, right
		 */
		final int deltaPos = 6;

		for (int i = -offset; i <= offset; i++) {
			for (int j = -offset; j <= offset; j++) {
				if (sideHit == 0 || sideHit == 1) setBlockAir(x + i, y, z + j, deltaPos, true);
				else if (sideHit == 2 || sideHit == 3) setBlockAir(x + i,  y + j,  z, deltaPos, true);
				else if (sideHit == 4 || sideHit == 5) setBlockAir(x, y + i, z + j, deltaPos, true);
			}
		} 

	}

	public void setShiftClick(boolean state) {
		this.shiftClick = state;
	}

	private void setBlock(int x, int y, int z) {
		setBlock(x, y, z, this.block);
	}

	private void setBlock(int x, int y, int z, Block block) {

		// How far should the player be able to 'reach'.
		int deltaPos = 4;
		boolean xCheck = false, yCheck = false, zCheck = false;

		/*
		 * Check the reach distance relative to the player and desired block placement.
		 */
		if ((x - deltaPos) < player.posX && player.posX < (x + deltaPos)) xCheck = true;
		if ((y - deltaPos) < player.posY && player.posY < (y + deltaPos)) yCheck = true;
		if ((z - deltaPos) < player.posZ && player.posZ < (z + deltaPos)) zCheck = true;

		/*
		 * If said block is something and the player can reach the block they are looking at, place the said block.
		 */
		if (block != null && xCheck && yCheck && zCheck) {
			if (!world.blockExists(x, y, z)) world.setBlock(x, y, z, block.blockID);
			else if (world.blockExists(x, y, z) && !blockBlackList.contains(bh.getBlock(x, y, z))) {
				// If the block trying to be placed is equal to block at the coordinate, return;
				if (world.getBlockId(x, y, z) == block.blockID) {
					setReturnBlock(true);
					return;
				}

				// Set true for par4 if destroyed block should drop, item-drops.
				// Makes sure that if we are trying to hoe dirt, there is no need to destroy the block.
				if (stack.getItem().itemID != ExtraTools.glowHoeUnbreakable.itemID) world.destroyBlock(x, y, z, true);
				world.setBlock(x, y, z, block.blockID);
			}

			else return;
		}
		else return;
	}
	
	private void setBlockAir(int x, int y, int z, boolean matSp) {
		setBlockAir(x, y, z, 4, matSp);
	}

	// Setting material to null disregards check for like material blocks.
	private void setBlockAir(int x, int y, int z, int deltaPos, boolean matSp) {
		
		// How far should the player be able to 'reach'.
		boolean xCheck = false, yCheck = false, zCheck = false;

		/*
		 * Check the reach distance relative to the player and desired block placement.
		 */
		if ((x - deltaPos) < player.posX && player.posX < (x + deltaPos)) xCheck = true;
		if ((y - deltaPos) < player.posY && player.posY < (y + deltaPos)) yCheck = true;
		if ((z - deltaPos) < player.posZ && player.posZ < (z + deltaPos)) zCheck = true;

		/*
		 * If said block is something and the player can reach the block they are looking at, place the said block.
		 */
		if (xCheck && yCheck && zCheck) {
			if (world.blockExists(x, y, z) && !blockBlackList.contains(bh.getBlock(x, y, z))) {
				// If the block trying to be placed is equal to block at the coordinate, return;

				// Set true for par4 if destroyed block should drop, item-drops.
				// Makes sure that if we are trying to hoe dirt, there is no need to destroy the block.
				if (!matSp) world.destroyBlock(x, y, z, true);
				else {
					Material currentMat = bh.getBlockMaterial(x, y, z);
					if (stack.getItem().itemID != ExtraTools.glowHammerUnbreakable.itemID || currentMat != Material.rock) return;
					else world.destroyBlock(x, y, z, true);
				}
				
				world.setBlockToAir(x, y, z);
			}

			else return;
		}
		else return;
	}

	private void setReturnBlock(boolean result) {
		this.returnBlock = result;
	}

	public boolean getReturnBlock() {
		return this.returnBlock;
	}

	private void tillLand(int x, int y, int z) {
		// Get all needed block ids'
		Block tilDir = Block.tilledField;
		int tilledDirtID = tilDir.blockID;
		int dirtID = Block.dirt.blockID;
		int grassID = Block.grass.blockID;

		/*
		 * Scan through blocks on the x and z axis, check if they can be tilled, till the land!
		 */
		for (int xx = x - 1; xx < x + 2; xx++) {
			for (int zz = z - 1; zz < z + 2; zz++) {
				int currentBlock = world.getBlockId(xx, y, zz);

				// Note: Last check below shouldn't be necessary as it should already be tilled! (in theory).
				if (currentBlock == dirtID || currentBlock == grassID /* || currentBlock == tilledDirtID */) {
					setBlock(xx, y, zz, tilDir);
				}
			}
		}
	}

	private void print(Object msg) {
		System.out.println("" + msg + ".");
	}

}
