package com.hockeyhurd.block.ores;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.mod.ExtraTools;

public class BlockGlowOre extends Block {

	public BlockGlowOre(Material material) {
		super(material);
		this.setBlockName("GlowOre");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightLevel(0.3f);
		this.setHardness(10);
		this.setResistance(5);
	}

	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowOre");
	}

	public Item getItemDropped(int par1, Random rand, int par3) {
		return Item.getItemFromBlock(this);
	}

	public int quantityDropped(Random rand) {
		return 1;
	}

}
