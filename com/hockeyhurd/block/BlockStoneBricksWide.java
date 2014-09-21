package com.hockeyhurd.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneBricksWide extends BlockStoneBricksDefault {

	public BlockStoneBricksWide(Material material) {
		super(material);
		this.setBlockName("StoneBricksWide");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "StoneBricks_wide");
	}

}
