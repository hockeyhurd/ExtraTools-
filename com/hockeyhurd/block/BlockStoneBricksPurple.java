package com.hockeyhurd.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneBricksPurple extends BlockStoneBricksDefault {

	public BlockStoneBricksPurple(Material material) {
		super(material);
		this.setBlockName("StoneBricksPurple");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(prefix + "_purple");
	}

}
