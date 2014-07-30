/* Note:
 * 	This class has nothing to do and/or
 * 	is associated with the mod WAILA.
 *  Just a simple class I made to help me
 *  figure out What Am I Looking At? :)
 */

package com.hockeyhurd.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;

public class Waila {

	private ItemStack stack;
	private World world;
	private EntityPlayer player;
	private Block block;
	private BlockHelper bh;
	private ItemHelper ih;
	private boolean placeBlock;
	private boolean shiftClick;
	private List<Block> blockBlackList;
	private Material[] matWhiteList;

	// Tools/items used.

	private int sideHit = 0;
	private int offset;
	private int metaData;
	private Vector3IHelper vec;
	private boolean returnState = false;

	public Waila(ItemStack itemStack, World world, EntityPlayer entityPlayer, Block block, boolean placeBlock, boolean shiftClick) {
		this(itemStack, world, entityPlayer, block, 0, placeBlock, shiftClick);
	}

	public Waila(ItemStack itemStack, World world, EntityPlayer entityPlayer, Block block, int metaData, boolean placeBlock, boolean shiftClick) {
		this.stack = itemStack;
		this.world = world;
		this.player = entityPlayer;
		this.block = block;
		this.metaData = metaData;
		this.bh = new BlockHelper(world, player);
		this.ih = new ItemHelper(world, player);
		this.placeBlock = placeBlock; 
		this.shiftClick = shiftClick;

		blockBlackList = new ArrayList<Block>();
		addBlockBlackList();

		matWhiteList = new Material[] {
		};

		offset = 0; // By default, don't offset anything.
	}

	private void addBlockBlackList() {
		add(ExtraTools.glowTorch);
		add(Blocks.torch);
		add(Blocks.rail);
		add(Blocks.activator_rail);
		add(Blocks.detector_rail);
		add(Blocks.golden_rail);
		add(Blocks.bedrock);

		// TODO: Find solution to determine if a block has a hardness of -1 (unbreakable).
		/*Iterator iter = Block.blockRegistry.iterator();
		while (iter.hasNext()) {
			Block block = (Block) iter.next();
			if (block != null && !blockBlackList.contains(block)) add(block);
		}*/

	}

	public void setMatWhiteList(Material[] mats) {
		this.matWhiteList = mats;
	}

	private void add(Block block) {
		blockBlackList.add(block);
	}

	public void setOffset(int offset) {
		// If the setOffsetVal > 0 set this.offSet = offset else, set this.offset = 0, (removing offset to faulty val).
		this.offset = (offset > 0 ? offset : 0);
	}

	public void finder() {
		finder(true);
	}

	public void finder(boolean handler) {
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
			MovingObjectPosition movingObjectPos = world.func_147447_a(vec3d, vec3d1, false, true, false);

			// Make sure there is no possibility the entity (player) is not
			// looking at 'null'.
			if (movingObjectPos == null) return;

			// Check if the vector ray intersects with some sort of TILE
			// if (movingObjectPos.typeOfHit == MovingObjectType.TILE) {
			if (movingObjectPos.typeOfHit == MovingObjectType.BLOCK) {

				// Get the posiotion of the TILE intersected as represented in
				// 3D space.
				int xx = movingObjectPos.blockX;
				int yy = movingObjectPos.blockY;
				int zz = movingObjectPos.blockZ;

				// Get the side of which the vector ray intersects with.
				int sideHit = movingObjectPos.sideHit;
				// print("Side: " + sideHit);

				if (handler) placeBlockHandler(world, xx, yy, zz, sideHit);
				else {
					setSideHit(sideHit);
					setVector3I(xx, yy, zz, sideHit);
				}
			}

			stack.setItemDamage(0);
		}
		return;
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
			if ((stack.getItem() == ExtraTools.glowHoeUnbreakable) && sideHit == 1) {

				// Get Block and block ids'
				Block tilDirt = Blocks.farmland;
				Block dirt = Blocks.dirt;
				Block grass = Blocks.grass;
				Block currentBlock = world.getBlock(xx, yy, zz);

				// Get the block the player is currently looking at
				if (currentBlock == dirt || currentBlock == grass || currentBlock == tilDirt) {

					/*
					 * if they shift click, till a 9 * 9 area else, hoe the block they are looking at
					 */
					if (shiftClick) tillLand(xx, yy, zz);
					else world.setBlock(xx, yy, zz, tilDirt);

					// Play world sound for all to hear :)
					world.playSoundEffect((double) (xx + 0.5), (double) (yy + 0.5), (double) (zz + 0.5), tilDirt.stepSound.getBreakSound(), (tilDirt.stepSound.getVolume() + 1.0f) / 2.0f, tilDirt.stepSound.getPitch() * 0.8f);
				}

				/*
				 * If the block the user is looking at cannot be tilled, don't do anything! (yet)
				 */
				else print("Block could not be tilled!");
			}

			else if (stack.getItem() == ExtraTools.glowHammerUnbreakable) mineArea(sideHit, xx, yy, zz);
			else if (stack.getItem() == ExtraTools.glowExcavatorUnbreakable) mineArea(sideHit, xx, yy, zz);

			// If don't place a block and player is not using a glowHoe and want to return the block being looked at?
			else {
				String blockName = bh.getBlock(xx, yy, zz).getLocalizedName();
				print(blockName);
			}

		}
	}

	@Deprecated
	private void setSideHit(int sideHit) {
		this.sideHit = sideHit;
	}
	
	public Vector3IHelper getVector3I() {
		return this.vec;
	}
	
	private void setVector3I(int x, int y, int z, int sideHit) {
		this.vec = new Vector3IHelper(x, y, z, sideHit);
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
				else if (sideHit == 2 || sideHit == 3) setBlockAir(x + i, y + j, z, deltaPos, true);
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
			if (!bh.blockExists(x, y, z)) world.setBlock(x, y, z, block);
			else if (bh.blockExists(x, y, z) && !blockBlackList.contains(bh.getBlock(x, y, z))) {
				// If the block trying to be placed is equal to block at the coordinate, return;
				if (bh.getBlock(x, y, z) == block && bh.getBlockMetaData(x, y, z) == this.metaData) return;

				// Set true for par4 if destroyed block should drop, item-drops.
				// Makes sure that if we are trying to hoe dirt, there is no need to destroy the block.
				if (stack.getItem() != ExtraTools.glowHoeUnbreakable) {
					Block b = bh.getBlock(x, y, z);
					bh.destroyBlock(x, y, z, false);
					world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(b, 1)));
				}

				// Args: x, y, z, blockID, blockMetadata,
				world.setBlock(x, y, z, block, this.metaData, 3);

				setResult(true);
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
			if (bh.blockExists(x, y, z) && !blockBlackList.contains(bh.getBlock(x, y, z))) {
				// If the block trying to be placed is equal to block at the coordinate, return;

				// Set true for par4 if destroyed block should drop, item-drops.
				// if (!matSp) world.func_147480_a(x, y, z, true);
				if (!matSp) bh.destroyBlock(x, y, z, true);
				else {
					Material currentMat = bh.getBlockMaterial(x, y, z);
					boolean contains = false;
					
					for (int i = 0; i < matWhiteList.length; i++) {
						if (matWhiteList[i] == currentMat) contains = true;
					}
					
					if ((stack.getItem() != ExtraTools.glowHammerUnbreakable && stack.getItem() != ExtraTools.glowExcavatorUnbreakable) || !contains) return;
					else bh.destroyBlock(x, y, z, true);
				}

				world.setBlockToAir(x, y, z);
			}

			else return;
		}
		else return;
	}

	private void setResult(boolean result) {
		this.returnState = result;
	}

	public boolean getResult() {
		return this.returnState;
	}

	private void tillLand(int x, int y, int z) {
		// Get all needed block ids'
		Block tilDir = Blocks.farmland;
		Block dirt = Blocks.dirt;
		Block grass = Blocks.grass;

		/*
		 * Scan through blocks on the x and z axis, check if they can be tilled, till the land!
		 */
		for (int xx = x - 1; xx < x + 2; xx++) {
			for (int zz = z - 1; zz < z + 2; zz++) {
				Block currentBlock = bh.getBlock(xx, y, zz);

				// Note: Last check below shouldn't be necessary as it should already be tilled! (in theory).
				if (currentBlock == dirt || currentBlock == grass /* || currentBlock == tilledDirtID */) {
					setBlock(xx, y, zz, tilDir);
				}
			}
		}
	}

	private void print(Object msg) {
		System.out.println("" + msg + ".");
	}

}
