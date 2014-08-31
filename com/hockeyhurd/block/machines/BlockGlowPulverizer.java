package com.hockeyhurd.block.machines;

import java.util.Random;

import net.minecraft.block.Block;
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

import com.hockeyhurd.entity.tileentity.TileEntityGlowPulverizer;
import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowPulverizer extends AbstractBlockMachine {

	@SideOnly(Side.CLIENT)
	private IIcon pulverizerFront, pulverizerTop;
	
	public BlockGlowPulverizer(Material material, boolean active) {
		super(material);
		this.active = active;
		this.setCreativeTab(!active ? ExtraTools.myCreativeTab : null);
		this.setBlockName("GlowPulverizer");
		this.setHardness(1.0f);
	}

	public TileEntity createNewTileEntity(World world, int id) {
		return new TileEntityGlowPulverizer();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowFurnace_side");
		this.pulverizerFront = reg.registerIcon(ExtraTools.assetsDir + (active ? "GlowPulverizer_front_on" : "GlowPulverizer_front_off"));
		this.pulverizerTop = reg.registerIcon(ExtraTools.assetsDir + "GlowFurnace_top");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 3 && metadata == 0) return this.pulverizerFront;
		return side == 1 ? this.pulverizerTop : (side == 0 ? this.pulverizerTop : (side != metadata ? this.blockIcon : this.pulverizerFront)); 
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;
		
		else {
			TileEntityGlowPulverizer te = (TileEntityGlowPulverizer) world.getTileEntity(x, y, z);
			if (te != null) FMLNetworkHandler.openGui(player, ExtraTools.instance, ExtraTools.guiIDGlowPulverizer, world, x, y, z);
			return true;
		}
	}

	public static void updateBlockState(boolean active, World world, int x, int y, int z) {
		int metaData = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		keepInventory = true;

		if (active) world.setBlock(x, y, z, ExtraTools.glowPulverizerOn);
		else world.setBlock(x, y, z, ExtraTools.glowPulverizerOff);

		keepInventory = false;
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
			((TileEntityGlowPulverizer) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
		}
	}
	
	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldBlockMetaData) {
		if (!keepInventory) {
			TileEntityGlowPulverizer tileEntityGlowPulverizer = (TileEntityGlowPulverizer) world.getTileEntity(x, y, z);

			if (tileEntityGlowPulverizer != null) {
				for (int j1 = 0; j1 < tileEntityGlowPulverizer.getSizeInventory(); j1++) {
					ItemStack stack = tileEntityGlowPulverizer.getStackInSlot(j1);

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

		// super.breakBlock(world, x, y, z, oldBlock, oldBlockMetaData);
	}
	
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ExtraTools.glowPulverizerOff);
	}

}
