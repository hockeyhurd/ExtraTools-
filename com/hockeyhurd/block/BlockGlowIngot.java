package com.hockeyhurd.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.extratools.ExtraTools;

public class BlockGlowIngot extends Block {
	
	private static final float defaultLightVal = 0.5f;
	
	public BlockGlowIngot(Material material) {
		super(material);
		this.setBlockName("GlowIngotBlock");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHardness(5f);
		this.setResistance(5f);
		this.setLightLevel(defaultLightVal);
	}
	
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "BlockGlowIngot");
	}

}
