package com.hockeyhurd.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;

public class BlockGlowIngot extends Block {
	
	private static final float defaultLightVal = 0.5f;
	
	public BlockGlowIngot(int id, Material material) {
		super(id, material);
		this.setUnlocalizedName("GlowIngotBlock");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHardness(5f);
		this.setResistance(5f);
		this.setLightValue(defaultLightVal);
	}
	
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "BlockGlowIngot");
	}

}
