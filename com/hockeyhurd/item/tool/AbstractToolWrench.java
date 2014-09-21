package com.hockeyhurd.item.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import com.hockeyhurd.block.machines.AbstractBlockMachine;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.entity.tileentity.AbstractTileEntityGlow;
import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.extratools.ExtraTools;
import com.hockeyhurd.util.*;
import com.hockeyhurd.util.math.Vector4Helper;

public abstract class AbstractToolWrench extends Item {

	protected TimerHelper th;
	protected EntitySpawnerHelper esh;
	protected Block[] wrenchables;
	protected Random random = new Random();

	public AbstractToolWrench() {
		super();
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setMaxStackSize(1);

		th = new TimerHelper();
		esh = new EntitySpawnerHelper();
		esh.init();
	}

	public abstract void registerIcons(IIconRegister reg);

	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (th.use) return stack;
		else {
			if (!world.isRemote) {
				BlockHelper bh = new BlockHelper(world, player);
				Waila waila = new Waila(stack, world, player, null, false, false);
				waila.finder();

				Vector4Helper<Integer> vec = null;
				if (waila.getVector4i() == null) return stack;
				else vec = waila.getVector4i();

				boolean contains = false;
				int metaData = 0;

				if (!bh.blockExists(vec)) return stack;
				else {
					Block currentBlock = bh.getBlock(vec);

					if (currentBlock == Blocks.mob_spawner && world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityMobSpawner) {
						contains = true;
						TileEntityMobSpawner te = (TileEntityMobSpawner) world.getTileEntity(vec.getX(), vec.getY(), vec.getZ());
						String entityToSpawn = te.func_145881_a().getEntityNameToSpawn();
						metaData = esh.getMappedID(entityToSpawn);
					}

					else if (currentBlock == Blocks.chest && world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityChest) {
						contains = true;
						TileEntityChest te = (TileEntityChest) world.getTileEntity(vec.getX(), vec.getY(), vec.getZ());
						int numSlots = te.getSizeInventory();
						List<ItemStack> stacksToDrop = new ArrayList<ItemStack>();

						for (int i = 0; i < numSlots; i++) {
							if (te.getStackInSlot(i) != null) stacksToDrop.add(te.getStackInSlot(i));
						}

						for (ItemStack subStack : stacksToDrop) {
							world.spawnEntityInWorld(new EntityItem(world, vec.getX() + random.nextInt(2), vec.getY() + random.nextInt(2), vec.getZ() + random.nextInt(2), subStack));
						}

						bh.destroyBlock(vec, true);
						return stack;
					}

					else if (currentBlock == ExtraTools.glowChest && world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityGlowChest) {
						contains = true;
						TileEntityGlowChest te = (TileEntityGlowChest) world.getTileEntity(vec.getX(), vec.getY(), vec.getZ());
						int numSlots = te.getSizeInventory();
						List<ItemStack> stacksToDrop = new ArrayList<ItemStack>();

						for (int i = 0; i < numSlots; i++) {
							if (te.getStackInSlot(i) != null) {
								stacksToDrop.add(te.getStackInSlot(i));
								te.setInventorySlotContents(i, (ItemStack) null);
							}
						}

						ItemStack theStack = new ItemStack(currentBlock, 1);
						if (stacksToDrop.size() > 0) handleWrenchNBT(theStack, stacksToDrop, world, player);

						EntityItem eItem = new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), theStack);
						world.spawnEntityInWorld(eItem);
						bh.destroyBlock(vec, false);
						return stack;
					}

					else if ((currentBlock instanceof BlockGlowFurnace || currentBlock instanceof AbstractBlockMachine)
							&& (world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof AbstractTileEntityGlow || (world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityGlowFurnace))) {
						LogHelper.info("Attempting to handle glow machine!");
						handleGlowMachines(stack, currentBlock, vec, world);
						return stack;
					}

					// If already handled, skip for loop.
					if (!contains) {
						for (int i = 0; i < wrenchables.length; i++) {
							if (wrenchables[i] != null && wrenchables[i] == currentBlock) {
								contains = true;
								break;
							}
						}
					}

					if (contains) {
						// Make sure metaData == something.
						world.spawnEntityInWorld(new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(currentBlock, 1, metaData > 0 ? metaData : 0)));
						bh.destroyBlock(vec);
					}
				}
			}
		}

		return stack;
	}

	protected void handleWrenchNBT(ItemStack stackInHand, List<ItemStack> stacks, World world, EntityPlayer player) {
		NBTTagCompound nbt = stackInHand.stackTagCompound;
		if (nbt == null) nbt = stackInHand.stackTagCompound = new NBTTagCompound();

		int[] idArray = new int[(7 * 9)];
		int[] stackSizeArray = new int[(7 * 9)];
		int index = 0;
		for (ItemStack stack : stacks) {
			int i = stack.getItem() != null ? Item.getIdFromItem(stack.getItem()) : 0;
			idArray[index] = i;

			int size = stack.getItem() != null ? stack.stackSize : 0;
			stackSizeArray[index] = size;

			index++;
		}

		nbt.setIntArray("Items", idArray);
		nbt.setIntArray("Sizes", stackSizeArray);
	}

	protected void handleGlowMachines(ItemStack stack, Block block, Vector4Helper<Integer> vec, World world) {
		int x = vec.getX();
		int y = vec.getY();
		int z = vec.getZ();

		byte[] array = new byte[] {
				2, 5, 3, 4
		};
		byte dir = 0;
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			index = i;
			if (world.getBlockMetadata(x, y, z) == array[i]) {
				if (++index < array.length) dir = (byte) array[index];
				else dir = (byte) array[index = 0];
			}
		}

		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
	}

}
