package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemItemReplacer extends Item {

	private TimerHelper th;
	
	public ItemItemReplacer(int id) {
		super(id);
		this.setUnlocalizedName("ItemReplacer");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setMaxStackSize(1);
		
		th = new TimerHelper(20, 2);
	}
	
	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "ItemReplacer");
	}
	
	// Makes sure the player can't press it more than once per second.
		public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
			th.update();
		}

		public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
			if (th.getUse() && !th.excuser()) return stack;
			
			Block block = null;
			int id = 0;
			ItemStack thisStack = null;
			
			for (int i = 0; i < player.inventory.getHotbarSize(); i++) {
				if (player.inventory.getStackInSlot(i).getItem() == this) {
					id = i + 1;
					
					if (player.inventory.getStackInSlot(id) == null) break;
					else {
						thisStack = player.inventory.getStackInSlot(id);
						block = Block.blocksList[thisStack.itemID];
						if (thisStack.stackSize > 0) thisStack.stackSize--;
						if (thisStack.stackSize < 1) player.inventory.setInventorySlotContents(id, null);
						break;
					}
				}
				
			}
			
			// If the desired block == null quit all opperation.
			if (block == null) {
				th.setUse(true);
				return stack;
			}
			
			// Desired Block musn't be null, therefore we can proceed.
			Waila waila = new Waila(stack, world, player, block, true, false);
			// if (!th.getUse() || th.excuser()) waila.finder();
			waila.finder();
			th.setUse(true);
			
			if (waila.getReturnBlock()) {
				if (thisStack.stackSize > 0) thisStack.stackSize++;
				else player.inventory.setInventorySlotContents(id, new ItemStack(block, 1));
			}
			return stack;
		}

		/*
		 * public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		 * 
		 * }
		 */

}
