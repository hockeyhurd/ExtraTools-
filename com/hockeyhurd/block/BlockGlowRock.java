package com.hockeyhurd.block;

import scala.runtime.DoubleRef;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.extratools.ClientProxy;
import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowRock extends Block {

	public BlockGlowRock(Material material) {
		super(material);
		this.setBlockName("GlowRock");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setLightLevel(0.8f);
		this.setHardness(0.5f);
		this.setResistance(5);
		this.setStepSound(soundTypeGlass);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowRock");
	}

	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProxy.glowRockRenderType;
	}

	@SideOnly(Side.CLIENT)
	public boolean canRenderInPass(int pass) {
		ClientProxy.renderPass = pass;
		return true;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

}
