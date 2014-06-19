package com.hockeyhurd.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

import com.hockeyhurd.main.ExtraTools;

public class BlockGlowRock extends Block {

	public BlockGlowRock(int id, Material material) {
		super(id, material);
		this.setUnlocalizedName("GlowRock");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightValue(0.8f);
		this.setHardness(0.5f);
		this.setResistance(5);
		this.setStepSound(soundGlassFootstep);
	}
	
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowRock");
	}

}
