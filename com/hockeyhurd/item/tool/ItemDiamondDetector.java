package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.ChunkHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.interfaces.IToolDetector;

public class ItemDiamondDetector extends AbstractToolDetector {


	public ItemDiamondDetector(Block blockToFind) {
		super(blockToFind);
		this.setUnlocalizedName("DiamondDetector");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "DiamondDetector");
	}


}
