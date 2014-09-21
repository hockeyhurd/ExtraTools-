package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.hockeyhurd.extratools.ExtraTools;

public class ItemDiamondDetector extends AbstractToolDetector {


	public ItemDiamondDetector(Block blockToFind) {
		super(blockToFind);
		this.setUnlocalizedName("DiamondDetector");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "DiamondDetector");
	}

}
