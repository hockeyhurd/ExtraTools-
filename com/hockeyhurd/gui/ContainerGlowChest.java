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
	private int numRows;

	public ContainerGlowChest(InventoryPlayer inv, TileEntityGlowChest entity) {
		this.lowerChestInventory = (IInventory) inv;
		this.glowChest = entity;
		this.numRows = entity.getSizeInventory() / 9;
		addSlots(inv, entity);
	}

	private void addSlots(InventoryPlayer inv, TileEntityGlowChest entity) {
		int size = 18;

		// Adds the main inventory for the chest gui.
		for (int y = 0; y < this.numRows; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(entity, x + y * 9, 8 + x * size, 18 + y * size));
			}
		}

		// Adds the 'player' inventory to chest's gui.
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, 8 + x * size, 158 + y * size));
			}
		}

		// Adds the hotbar slots to the gui.
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inv, i, 8 + i * size, 216)); // 198
		}

	}

	// Player shift-click a slot.
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) this.inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			final int mainInvSlotID = this.numRows * 9;

			// merges the item into player inventory since its in the tileEntity
			if (slot < mainInvSlotID) {
				if (!this.mergeItemStack(stackInSlot, mainInvSlotID, this.inventorySlots.size(), true)) return null;
			}
			// places it into the tileEntity is possible since its in the player inventory
			else if (!this.mergeItemStack(stackInSlot, 0, mainInvSlotID, false)) return null;

			if (stackInSlot.stackSize == 0) slotObject.putStack((ItemStack) null);
			else slotObject.onSlotChanged();

			if (stackInSlot.stackSize == stack.stackSize) return null;
			slotObject.onPickupFromSlot(player, stackInSlot);
		}

		return stack;
	}

	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	/*
	 * public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) { return super.mergeItemStack(stack, start, end, reverse); }
	 */

	public IInventory getLowerChestInventory() {
		return this.lowerChestInventory;
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.lowerChestInventory.closeInventory();
		this.glowChest.closeInventory();
	}

}
