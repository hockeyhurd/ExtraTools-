package com.hockeyhurd.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;

public class ContainerGlowChest extends Container {

	private TileEntityGlowChest glowChest;
	private IInventory lowerChestInventory;

	public ContainerGlowChest(InventoryPlayer inv, TileEntityGlowChest entity) {
		this.glowChest = entity;
		addSlots(inv, entity);
	}

	private void addSlots(InventoryPlayer inv, TileEntityGlowChest entity) {
		// Adds the inventory to furnace's gui.
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Adds the hotbar slots to the gui.
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142)); // 198
		}

	}

	// Player shift-click a slot.
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if (index < 8) {
				if (!this.mergeItemStack(slotStack, 8, this.inventorySlots.size(), false)) return null;
			}

			else if (!this.getSlot(0).isItemValid(slotStack) || !this.mergeItemStack(slotStack, 0, 4, false)) return null;

			if (slotStack.stackSize == 0) slot.putStack((ItemStack) null);
			else slot.onSlotChanged();

			if (slotStack.stackSize == stack.stackSize) return null;
			slot.onPickupFromSlot(player, slotStack);
		}

		return stack;
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
		return super.mergeItemStack(stack, start, end, reverse);
	}
	
	public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }

}
