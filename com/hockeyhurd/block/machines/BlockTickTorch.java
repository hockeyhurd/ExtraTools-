package com.hockeyhurd.block.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hockeyhurd.entity.tileentity.TileEntityTickTorch;
import com.hockeyhurd.extratools.ExtraTools;
import com.hockeyhurd.util.ChatHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTickTorch extends BlockTorch implements ITileEntityProvider {

	private ChatHelper chatHelp;
	
	public BlockTickTorch() {
		super();
		this.setBlockName("TickTorch");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightLevel(1.0f);
		this.setResistance(5f);
		this.setStepSound(soundTypeGlass);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "tick_torch_on");
	}
	
	public int quantityDropped(Random random) {
		return 1;
	}
	
	public TileEntity createNewTileEntity(World world, int id) {
		return new TileEntityTickTorch();
	}
	
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		super.randomDisplayTick(world, x, y, z, random);
	}
	
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileEntityTickTorch) ((TileEntityTickTorch) te).setActive(!world.isBlockIndirectlyGettingPowered(x, y, z));
		}
		
		super.onBlockAdded(world, x, y, z);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2, float par3, float par4) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te == null || !(te instanceof TileEntityTickTorch)) return false;
			TileEntityTickTorch torch = (TileEntityTickTorch) te;
			if (chatHelp == null) chatHelp = new ChatHelper();
			if (torch.updateMode(player.isSneaking())) player.addChatComponentMessage(chatHelp.comp("Changed mode to: " + torch.getMode()));
		}
		
		return false;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileEntityTickTorch) ((TileEntityTickTorch) te).setActive(!world.isBlockIndirectlyGettingPowered(x, y, z));
		}
		
		super.onNeighborBlockChange(world, x, y, z, block);
	}

}