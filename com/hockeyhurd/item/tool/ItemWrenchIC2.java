package com.hockeyhurd.item.tool;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import com.hockeyhurd.block.machines.AbstractBlockMachine;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.entity.tileentity.AbstractTileEntityGlow;
import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.*;
import com.hockeyhurd.util.math.Vector4Helper;

public class ItemWrenchIC2 extends AbstractToolWrench {

	private Block[] wrenchables;
	private Block theBlock;
	private int energyStored;

	public ItemWrenchIC2() {
		super();
		this.setUnlocalizedName("Wrench");
		this.setMaxStackSize(1);
		this.setMaxDamage(0);

		th = new TimerHelper();
		esh = new EntitySpawnerHelper();
		esh.init();
		this.wrenchables = ExtraTools.ch.getBlockWrenchArray();
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "Wrench");
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!player.isSneaking()) return stack;

		if (!world.isRemote) {
			BlockHelper bh = new BlockHelper(world, player);
			Waila waila = new Waila(stack, world, player, null, false, false);
			if (!th.getUse() || th.excuser()) {
				waila.finder(false);
				Vector4Helper<Integer> vec = waila.getVector4i();
				int metaData = 0;

				if (bh.blockExists(vec)) {
					boolean contains = false;
					boolean flag = false;
					boolean machine = false;
					Block currentBlock = bh.getBlock(vec);

					if (world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityBlock) {
						contains = true;
						TileEntityBlock te = (TileEntityBlock) world.getTileEntity(vec.getX(), vec.getY(), vec.getZ());
						metaData = te.blockMetadata;
						flag = metaData > 0;

						if (te instanceof TileEntityElectricBlock) {
							machine = true;
							TileEntityElectricBlock te2 = (TileEntityElectricBlock) te;
							theBlock = te2.blockType;

							energyStored = te2.getStored();
						}
					}

					else if (currentBlock == Blocks.mob_spawner && world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityMobSpawner) {
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
							if ((wrenchables[i] != null && wrenchables[i] == currentBlock)) {
								contains = true;
								break;
							}
						}
					}

					if (contains) {
						if (machine) {
							if (flag) world.spawnEntityInWorld(new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(currentBlock, 1, metaData)));
							bh.destroyBlock(vec, flag ? false : true);
							return stack;
						}
						else world.spawnEntityInWorld(new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(currentBlock, 1, metaData)));
						bh.destroyBlock(vec, false);
					}
				}

			}

			th.setUse(true);
		}

		return stack;
	}

}
