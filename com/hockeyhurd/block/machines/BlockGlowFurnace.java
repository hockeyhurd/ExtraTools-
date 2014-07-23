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

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowFurnace extends BlockContainer {

	private final boolean active;
	private static boolean keepFurnaceInventory;
	private final Random furnaceRand = new Random();

	@SideOnly(Side.CLIENT)
	private IIcon furnaceFront, furnaceTop;

	public BlockGlowFurnace(Material material, boolean active) {
		super(material);
		this.active = active;
		this.setBlockName(active ? "GlowFurnaceOn" : "GlowFurnaceOff");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public Item getItemDropped(int par1, Random random, int par3) {
		return Item.getItemFromBlock(ExtraTools.glowFurnaceOff);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowFurnace_side");
		this.furnaceFront = reg.registerIcon(ExtraTools.modPrefix + (active ? "GlowFurnace_front_on" : "GlowFurnace_front_off"));
		this.furnaceTop = reg.registerIcon(ExtraTools.modPrefix + "GlowFurnace_top");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 3) return this.furnaceFront;
		return side == 1 ? this.furnaceTop : (side == 0 ? this.furnaceTop : (side != metadata ? this.blockIcon : this.furnaceFront));
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;
		
		else {
			TileEntityGlowFurnace teGF = (TileEntityGlowFurnace) world.getTileEntity(x, y, z);
			if (teGF != null) FMLNetworkHandler.openGui(player, ExtraTools.instance, ExtraTools.guiIDGlowFurnace, world, x, y, z);
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

	public static void updateFurnaceBlockState(boolean active, World world, int x, int y, int z) {
		int metaData = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		keepFurnaceInventory = true;

		if (active) {
			world.setBlock(x, y, z, ExtraTools.glowFurnaceOn);
		}
		else {
			world.setBlock(x, y, z, ExtraTools.glowFurnaceOff);
		}

		keepFurnaceInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, metaData, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}

	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (this.active) {
			int l = world.getBlockMetadata(x, y, z);
			float f = (float) x + 0.5F;
			float f1 = (float) y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float f2 = (float) z + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;

			if (l == 4) {
				world.spawnParticle("smoke", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
			}
			else if (l == 5) {
				world.spawnParticle("smoke", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
			}
			else if (l == 2) {
				world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
			}
			else if (l == 3) {
				world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityGlowFurnace();
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
			((TileEntityGlowFurnace) world.getTileEntity(x, y, z)).func_145951_a(stack.getDisplayName());
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldBlockMetaData) {
		if (!keepFurnaceInventory) {
			TileEntityGlowFurnace tileEntityGlowFurnace = (TileEntityGlowFurnace) world.getTileEntity(x, y, z);

			if (tileEntityGlowFurnace != null) {
				for (int j1 = 0; j1 < tileEntityGlowFurnace.getSizeInventory(); j1++) {
					ItemStack stack = tileEntityGlowFurnace.getStackInSlot(j1);

					if (stack != null) {
						float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

						while (stack.stackSize > 0) {
							int k1 = this.furnaceRand.nextInt(21) + 10;

							if (k1 > stack.stackSize) {
								k1 = stack.stackSize;
							}

							stack.stackSize -= k1;
							EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(stack.getItem(), k1, stack.getItemDamage()));

							if (stack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (double) ((float) this.furnaceRand.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.furnaceRand.nextGaussian() * f3 + 0.2F);
							entityitem.motionZ = (double) ((float) this.furnaceRand.nextGaussian() * f3);
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
		return Item.getItemFromBlock(ExtraTools.glowFurnaceOff);
	}

}
