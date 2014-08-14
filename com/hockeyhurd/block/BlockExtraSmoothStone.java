package com.hockeyhurd.block;

import java.util.List;

import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class BlockExtraSmoothStone extends Block {

	public BlockExtraSmoothStone(Material material) {
		super(material);
		this.setBlockName("ExtraSmoothStone");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHardness(1.0f);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		// blockIcon = reg.registerIcon(Blocks.stone.getItemIconName());
		for (int i = 0; i < 6; i++) {
			blockIcon = Blocks.stone.getIcon(i, 0);
		}
	}

	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
}
