package com.hockeyhurd.block;

import com.hockeyhurd.mod.ExtraTools;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneBricksRed extends BlockStoneBricksDefault {

	public BlockStoneBricksRed(Material material) {
		super(material);
		this.setBlockName("StoneBricksRed");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "StoneBricks_red");
	}

}
