package com.hockeyhurd.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExtraSmoothStone extends Block {

	public BlockExtraSmoothStone(Material material) {
		super(material);
		this.setBlockName("ExtraSmoothStone");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHardness(1.0f);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		// blockIcon = reg.registerIcon(Blocks.stone.getItemIconName());
		for (int i = 0; i < 6; i++) {
			blockIcon = Blocks.stone.getIcon(i, 0);
		}
	}

	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
}
