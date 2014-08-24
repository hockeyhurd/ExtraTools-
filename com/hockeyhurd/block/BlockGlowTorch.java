package com.hockeyhurd.block;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.mod.ExtraTools;

public class BlockGlowTorch extends BlockTorch {

	public BlockGlowTorch() {
		super();
		this.setBlockName("GlowTorch");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightLevel(1.0f);
		this.setResistance(5f);
		this.setStepSound(soundTypeGlass);
	}
	
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "torch_on");
	}
	
	public int quantityDropped(Random random) {
		return 0;
	}

}
