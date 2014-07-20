package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

import cpw.mods.fml.common.FMLCommonHandler;

public class ItemItemReplacer extends Item {

	private TimerHelper th;

	public ItemItemReplacer() {
		super();
		this.setUnlocalizedName("ItemReplacer");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setMaxStackSize(1);

		th = new TimerHelper(20, 2);
	}

	public void registerIcons(IIconRegister reg) {
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
		BlockHelper bh = new BlockHelper(world, player);

		for (int i = 0; i < player.inventory.getHotbarSize(); i++) {
			if (player.inventory.getStackInSlot(i) == null) continue;
			if (player.inventory.getStackInSlot(i).getItem() == this) {
				id = i + 1;
				if (player.inventory.getStackInSlot(id) == null) break;
				Item item_block = player.inventory.getStackInSlot(id).getItem();
				
				if (!bh.blockListContains(item_block.getIdFromItem(item_block)) && !th.getUse()) {
					// player.sendChatToPlayer(new ChatHelper().comp("Cannot place an item!"));
					break;
				}
				else {
					thisStack = player.inventory.getStackInSlot(id);
					block = bh.getBlockFromID(thisStack.getItem().getIdFromItem(thisStack.getItem()));
					break;
				}
			}

		}

		// If the desired block == null quit all operation.
		if (block == null) {
			th.setUse(true);
			return stack;
		}

		Waila waila = new Waila(stack, world, player, block, thisStack.getItemDamage(), true, false);
		if (!world.isRemote) waila.finder();
		th.setUse(true);

		// If the result of destroying and setting of a block was successful, do something.
		if (waila.getResult()) {
			FMLCommonHandler.instance().onPlayerPreTick(player);
			
			// While there is still items left in the given slot, decrease the stack by 1.
			if (thisStack.stackSize > 0) thisStack.stackSize--;

			// If there are no items left, make sure the slot becomes empty!
			if (thisStack.stackSize < 1) {
				thisStack.stackSize = 0;
				player.inventory.setInventorySlotContents(id, (ItemStack) null);
			}
			
			player.onUpdate();
			FMLCommonHandler.instance().onPlayerPostTick(player);
			
		}

		return stack;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Replaces block looking at with item in slot to the right!");
	}

}
