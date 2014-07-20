package com.hockeyhurd.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.mod.ExtraTools;

public class BlockGlowRock extends Block {

	public BlockGlowRock(Material material) {
		super(material);
		this.setBlockName("GlowRock");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightLevel(0.8f);
		this.setHardness(0.5f);
		this.setResistance(5);
		this.setStepSound(soundTypeGlass);
	}
	
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowRock");
	}

}
