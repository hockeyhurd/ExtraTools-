package com.hockeyhurd.block;

import java.util.Random;

import com.hockeyhurd.mod.ExtraTools;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

public class BlockSafeGlass extends BlockGlass {

	public BlockSafeGlass(Material material, boolean flag) {
		super(material, flag);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setBlockName("SafeGlass");
		this.setHardness(0.3f);
		this.setStepSound(soundTypeGlass);
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

}
