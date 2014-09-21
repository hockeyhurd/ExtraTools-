package com.hockeyhurd.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneBricksDefault extends Block {
	
	protected static final String prefix = ExtraTools.assetsDir + "StoneBricks";
	
	public BlockStoneBricksDefault(Material material) {
		super(material);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setBlockName("StoneBricksDefault");
		this.setHardness(1.5f);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "StoneBricks_default");
	}

}
