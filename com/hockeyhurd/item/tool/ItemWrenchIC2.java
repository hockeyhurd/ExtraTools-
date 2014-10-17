package com.hockeyhurd.item.tool;

import ic2.api.tile.IWrenchable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

	@SideOnly(Side.CLIENT)
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
				boolean containsBlock = false;
				boolean dropBlock = false;

				if (bh.blockExists(vec)) {
					if (!(world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntity)) return stack;
					Block currentBlock = bh.getBlock(vec);
					TileEntity te = world.getTileEntity(vec.getX(), vec.getY(), vec.getZ());

					if (te instanceof IWrenchable) {
						containsBlock = true;
						IWrenchable wrenchable = (IWrenchable) te;
						if (wrenchable.wrenchCanRemove(player)) {
							ItemStack theStack = wrenchable.getWrenchDrop(player);
							if (theStack != null) {
								world.spawnEntityInWorld(new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), theStack));
								dropBlock = false;
							}
						}
					}

					else if (currentBlock == ExtraTools.glowChest && te instanceof TileEntityGlowChest) {
						containsBlock = true;
						TileEntityGlowChest teChest = (TileEntityGlowChest) te;
						int numSlots = teChest.getSizeInventory();
						List<ItemStack> stacksToDrop = new ArrayList<ItemStack>();

						for (int i = 0; i < numSlots; i++) {
							if (teChest.getStackInSlot(i) != null) {
								stacksToDrop.add(teChest.getStackInSlot(i));
								teChest.setInventorySlotContents(i, (ItemStack) null);
							}
						}

						ItemStack theStack = new ItemStack(currentBlock, 1);
						if (stacksToDrop.size() > 0) handleWrenchNBT(theStack, stacksToDrop, world, player);

						EntityItem eItem = new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), theStack);
						world.spawnEntityInWorld(eItem);
						bh.destroyBlock(vec);
					}

					else if ((currentBlock instanceof BlockGlowFurnace || currentBlock instanceof AbstractBlockMachine)
							&& (world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof AbstractTileEntityGlow || (world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityGlowFurnace))) {
						containsBlock = true;
						LogHelper.info("Attempting to handle glow machine!");
						handleGlowMachines(stack, currentBlock, vec, world);
						dropBlock = false;
						th.setUse(true);
						return stack;
					}

					else if (currentBlock == Blocks.mob_spawner && world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityMobSpawner) {
						containsBlock = true;
						TileEntityMobSpawner teMB = (TileEntityMobSpawner) te;
						String entityToSpawn = teMB.func_145881_a().getEntityNameToSpawn();
						metaData = esh.getMappedID(entityToSpawn);
					}

					else return stack;

					// If already handled, skip for loop.
					if (!containsBlock) {
						for (int i = 0; i < wrenchables.length; i++) {
							if ((wrenchables[i] != null && wrenchables[i] == currentBlock)) {
								containsBlock = true;
								break;
							}
						}
					}

					else {
						if (dropBlock) world.spawnEntityInWorld(new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(currentBlock, 1, metaData)));
						bh.destroyBlock(vec, dropBlock);
					}

				}

				th.setUse(true);
			}
		}
		return stack;
	}
}
