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
		this.glowChest = entity;
		addSlots(inv, entity);
		this.numRows = entity.getSizeInventory() / 9;
	}

	private void addSlots(InventoryPlayer inv, TileEntityGlowChest entity) {
		int size = 18;
		
		// Adds the main inventory for the chest gui.
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(entity, x + y * 9 + 9, 8 + x * size, 18 + y * size));
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
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		// Vanilla code reference
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.numRows * 9) {
				if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) { return null; }
			}
			else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) return null;

			if (itemstack1.stackSize == 0) slot.putStack((ItemStack) null);
			else slot.onSlotChanged();
		}

		return itemstack;
		
		
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

	/**
	 * Called when the container is closed.
	 */
	/*public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.lowerChestInventory.closeInventory();
	}*/

}
