package com.hockeyhurd.block;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IconRegister;

import com.hockeyhurd.main.ExtraTools;

public class BlockGlowTorch extends BlockTorch {

	public BlockGlowTorch(int id) {
		super(id);
		this.setUnlocalizedName("GlowTorch");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightValue(1.0f);
		this.setResistance(5f);
		this.setStepSound(soundGlassFootstep);
	}
	
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "torch_on");
	}
	
	public int quantityDropped(Random random) {
		return 0;
	}

}
