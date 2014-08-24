package com.hockeyhurd.block.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
		this.setHardness(2f);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowChest");
		// blockIcon = reg.registerIcon("planks_oak");
	}

	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return 22;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;

		else {
			TileEntityGlowChest teGC = (TileEntityGlowChest) world.getTileEntity(x, y, z);
			if (teGC != null) {
				FMLNetworkHandler.openGui(player, ExtraTools.instance, ExtraTools.guiIDGlowChest, world, x, y, z);
				// player.openGui(ExtraTools.instance, ExtraTools.guiIDGlowChest, world, x, y, z);
				teGC.openInventory();
			}
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

		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) nbt = stack.stackTagCompound = new NBTTagCompound();
		TileEntityGlowChest te = (TileEntityGlowChest) world.getTileEntity(x, y, z);
		if (te == null) return;

		int length = nbt.getIntArray("Items").length;
		if (length <= 0) {
			System.out.println("Has no data!");
			return;
		}
		
		int index = 0;
		for (int i = 0; i < length; i++) {
			int id = nbt.getIntArray("Items")[i];
			int size = nbt.getIntArray("Sizes")[i];
			if (id <= 0 || size <= 0) continue;
			ItemStack tempStack = new ItemStack(Item.getItemById(id), size);
			// if (Item.getItemById(id) != null) tempStack = new ItemStack(Item.getItemById(id), size);
			te.setInventorySlotContents(9 + index++, tempStack);
		}
		
	}

	public void func_149954_e(World p_149954_1_, int p_149954_2_, int p_149954_3_, int p_149954_4_) {
		if (!p_149954_1_.isRemote) {
			Block block = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
			Block block1 = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
			Block block2 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
			Block block3 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
			boolean flag = true;
			int l;
			Block block4;
			int i1;
			Block block5;
			boolean flag1;
			byte b0;
			int j1;

			if (block != this && block1 != this) {
				if (block2 != this && block3 != this) {
					b0 = 3;

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
				}
				else {
					l = block2 == this ? p_149954_2_ - 1 : p_149954_2_ + 1;
					block4 = p_149954_1_.getBlock(l, p_149954_3_, p_149954_4_ - 1);
					i1 = block2 == this ? p_149954_2_ - 1 : p_149954_2_ + 1;
					block5 = p_149954_1_.getBlock(i1, p_149954_3_, p_149954_4_ + 1);
					b0 = 3;
					flag1 = true;

					if (block2 == this) {
						j1 = p_149954_1_.getBlockMetadata(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
					}
					else {
						j1 = p_149954_1_.getBlockMetadata(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
					}

					if (j1 == 2) {
						b0 = 2;
					}

					if ((block.func_149730_j() || block4.func_149730_j()) && !block1.func_149730_j() && !block5.func_149730_j()) {
						b0 = 3;
					}

					if ((block1.func_149730_j() || block5.func_149730_j()) && !block.func_149730_j() && !block4.func_149730_j()) {
						b0 = 2;
					}
				}
			}
			else {
				l = block == this ? p_149954_4_ - 1 : p_149954_4_ + 1;
				block4 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, l);
				i1 = block == this ? p_149954_4_ - 1 : p_149954_4_ + 1;
				block5 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, i1);
				b0 = 5;
				flag1 = true;

				if (block == this) {
					j1 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
				}
				else {
					j1 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
				}

				if (j1 == 4) {
					b0 = 4;
				}

				if ((block2.func_149730_j() || block4.func_149730_j()) && !block3.func_149730_j() && !block5.func_149730_j()) {
					b0 = 5;
				}

				if ((block3.func_149730_j() || block5.func_149730_j()) && !block2.func_149730_j() && !block4.func_149730_j()) {
					b0 = 4;
				}
			}

			p_149954_1_.setBlockMetadataWithNotify(p_149954_2_, p_149954_3_, p_149954_4_, b0, 3);
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
							if (k1 > stack.stackSize) k1 = stack.stackSize;

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
