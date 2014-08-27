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

import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AbstractBlockMachine extends BlockContainer {

	protected boolean active;
	protected static boolean keepInventory;
	protected Random random = new Random();
	
	public AbstractBlockMachine(Material material) {
		super(material);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHardness(1.0f);
	}

	@SideOnly(Side.CLIENT)
	public abstract void registerBlockIcons(IIconRegister reg);
	
	@SideOnly(Side.CLIENT)
	public abstract IIcon getIcon(int side, int metaData);
	
	/**
	 * @see com.hockeyhurd.block.machines.BlockFurnace for more information
	 */
	public abstract boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ);
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}
	
	protected void setDefaultDirection(World world, int x, int y, int z) {
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
	
	public static void updateBlockState(boolean active, World world, int x, int y, int z) {
		int metaData = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		keepInventory = true;

		if (active) world.setBlock(x, y, z, ExtraTools.glowFurnaceOn);
		else world.setBlock(x, y, z, ExtraTools.glowFurnaceOff);

		keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, metaData, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}
	
	/**
	 * Used for adding particle effectes while being active.
	 */
	public abstract void randomDisplayTick(World world, int x, int y, int z, Random random);
	
	public abstract TileEntity createNewTileEntity(World world, int p_149915_2_);
	
	/**
	 * Contains code for reference and should be overwritten by all child classes!
	 * Most notably should be changed is the TileEntity casting to correct type.
	 */
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
			((TileEntityGlowFurnace) world.getTileEntity(x, y, z)).func_145951_a(stack.getDisplayName());
		}
	}
	
	/**
	 * Contains code for reference and should be overwritten by all child classes!
	 * Most notably should be changed is the TileEntity casting to correct type.
	 */
	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldBlockMetaData) {
		if (!keepInventory) {
			TileEntityGlowFurnace tileEntityGlowFurnace = (TileEntityGlowFurnace) world.getTileEntity(x, y, z);

			if (tileEntityGlowFurnace != null) {
				for (int j1 = 0; j1 < tileEntityGlowFurnace.getSizeInventory(); j1++) {
					ItemStack stack = tileEntityGlowFurnace.getStackInSlot(j1);

					if (stack != null) {
						float f = this.random.nextFloat() * 0.8F + 0.1F;
						float f1 = this.random.nextFloat() * 0.8F + 0.1F;
						float f2 = this.random.nextFloat() * 0.8F + 0.1F;

						while (stack.stackSize > 0) {
							int k1 = this.random.nextInt(21) + 10;

							if (k1 > stack.stackSize) {
								k1 = stack.stackSize;
							}

							stack.stackSize -= k1;
							EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(stack.getItem(), k1, stack.getItemDamage()));

							if (stack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.2F);
							entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
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
	
	/**
	 * Should return Item.getItemFromBlock(this block);
	 */
	@SideOnly(Side.CLIENT)
	public abstract Item getItem(World world, int x, int y, int z);
}
