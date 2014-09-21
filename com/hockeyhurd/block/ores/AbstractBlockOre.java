package com.hockeyhurd.block.ores;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AbstractBlockOre extends Block {

	protected final float hardness = 5.0f;
	protected String fileName; 
	
	public AbstractBlockOre(Material material, String name) {
		super(material);
		this.setBlockName(name);
		this.setHardness(this.hardness);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.fileName = ExtraTools.assetsDir + name;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(fileName);
	}
	
	public Item getItemDropped(int par1, Random rand, int par3) {
		return Item.getItemFromBlock(this);
	}

	public int quantityDropped(Random rand) {
		return 1;
	}

}
