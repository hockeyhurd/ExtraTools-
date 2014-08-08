package com.hockeyhurd.block.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowChest extends BlockContainer {

	private static boolean keepChestInventory;
	private Random chestRand = new Random();

	@SideOnly(Side.CLIENT)
	private IIcon chestFront, chestTop;

	public BlockGlowChest(Material material) {
		super(material);
		this.setBlockName("GlowChest");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		// blockIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowChest");
		blockIcon = reg.registerIcon("planks_oak");
		// TODO: register other block icons stuff.
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;

		else {
			TileEntityGlowChest teGC = (TileEntityGlowChest) world.getTileEntity(x, y, z);
			if (teGC != null) FMLNetworkHandler.openGui(player, ExtraTools.instance, ExtraTools.guiIDGlowChest, world, x, y, z);
			return true;
		}

	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block block = world.getBlock(x, y, z - 1);
			Block block1 = world.getBlock(x, y, z + 1);
			Block block2 = world.getBlock(x - 1, y, z);
			Block block3 = world.getBlock(x + 1, y, z);
			byte b0 = 3;

			if (block.func_149730_j() && !block1.func_149730_j()) {
				b0 = 3;
			}

			if (block1.func_149730_j() && !block.func_149730_j()) {
				b0 = 2;
			}

			if (block2.func_149730_j() && !block3.func_149730_j()) {
				b0 = 5;
			}

			if (block3.func_149730_j() && !block2.func_149730_j()) {
				b0 = 4;
			}

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	public static void updateChestBlockState(boolean active, World world, int x, int y, int z) {
		int metaData = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		keepChestInventory = true;

		world.setBlock(x, y, z, ExtraTools.glowChest);

		keepChestInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, metaData, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}

	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityGlowChest();
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		if (stack.hasDisplayName()) {
			((TileEntityGlowChest) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldBlockMetaData) {
		if (!keepChestInventory) {
			TileEntityGlowChest tileEntityGlowChest = (TileEntityGlowChest) world.getTileEntity(x, y, z);

			if (tileEntityGlowChest != null) {
				for (int j1 = 0; j1 < tileEntityGlowChest.getSizeInventory(); j1++) {
					ItemStack stack = tileEntityGlowChest.getStackInSlot(j1);

					if (stack != null) {
						float f = this.chestRand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.chestRand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.chestRand.nextFloat() * 0.8F + 0.1F;

						while (stack.stackSize > 0) {
							int k1 = this.chestRand.nextInt(21) + 10;

							if (k1 > stack.stackSize) {
								k1 = stack.stackSize;
							}

							stack.stackSize -= k1;
							EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(stack.getItem(), k1, stack.getItemDamage()));

							if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());

							float f3 = 0.05F;
							entityitem.motionX = (double) ((float) this.chestRand.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.chestRand.nextGaussian() * f3 + 0.2F);
							entityitem.motionZ = (double) ((float) this.chestRand.nextGaussian() * f3);
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, oldBlock);
			}
		}

		super.breakBlock(world, x, y, z, oldBlock, oldBlockMetaData);
	}

	public boolean hasComparatorInputOverride() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ExtraTools.glowChest);
	}

}
