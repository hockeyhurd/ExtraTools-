package com.hockeyhurd.block.machines;

import java.util.Random;

import com.hockeyhurd.entity.tileentity.TileEntityGlowPulverizer;
import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGlowPulverizer extends AbstractBlockMachine {

	@SideOnly(Side.CLIENT)
	private IIcon furnaceFront, furnaceTop;
	
	public BlockGlowPulverizer(Material material, boolean active) {
		super(material);
		this.active = active;
	}

	public TileEntity createNewTileEntity(World world, int id) {
		return new TileEntityGlowPulverizer();
	}

	
	/*@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		// blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowPulverizer");
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowFurnace");
	}*/

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowFurnace_side");
		this.furnaceFront = reg.registerIcon(ExtraTools.assetsDir + (active ? "GlowFurnace_front_on" : "GlowFurnace_front_off"));
		this.furnaceTop = reg.registerIcon(ExtraTools.assetsDir + "GlowFurnace_top");
	}
	
	/*@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metaData) {
		return this.blockIcon;
	}*/

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 3 && metadata == 0) return this.furnaceFront;
		return side == 1 ? this.furnaceTop : (side == 0 ? this.furnaceTop : (side != metadata ? this.blockIcon : this.furnaceFront)); 
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

	
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ExtraTools.glowPulverizerOff);
	}

}
