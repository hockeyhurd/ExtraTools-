package com.hockeyhurd.mod;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

import com.hockeyhurd.block.ores.BlockGlowOre;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowOreNether extends BlockGlowOre {

	private IIcon overlay;
	private int pass = 0;

	public BlockGlowOreNether(Material material, String name) {
		super(material, name);
	}

}
