package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.ChunkHelper;
import com.hockeyhurd.util.Waila;

public class ItemDiamondDetector extends Item {

	private boolean inUse = false;
	
	public ItemDiamondDetector(int id) {
		super(id);
		this.setUnlocalizedName("DiamondDetector");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "DiamondDetector");
	}
	
	int time = 20;
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		if (!inUse) return;
		if (time > 0 && inUse) time--;
		if (time == 0 && inUse) {
			time = 20;
			inUse = false;
		}
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!inUse) {
			ChunkHelper chunkHelper = new ChunkHelper(world, player);
			chunkHelper.searchChunk(Block.oreDiamond);
		}
		inUse = true;
		return stack;
	}

}
