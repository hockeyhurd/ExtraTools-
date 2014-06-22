package com.hockeyhurd.block.ores;

import java.util.Random;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockGlowOre extends Block {

	public BlockGlowOre(int id, Material material) {
		super(id, material);
		this.setUnlocalizedName("GlowOre");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightValue(0.3f);
		this.setHardness(10);
		this.setResistance(5);
	}
	
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowOre");
	}
	
	public int idDropped(int par1, Random rand, int par3) {
		return this.blockID;
	}
	
	public int quantityDropped(Random rand) {
		return 1;
	}
	
}
