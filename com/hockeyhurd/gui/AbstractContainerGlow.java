package com.hockeyhurd.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.entity.tileentity.AbstractTileEntityGlow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AbstractContainerGlow extends Container {

	protected AbstractTileEntityGlow glowEntity;

	// Time left for this furnace to burn for.
	public int lastBurnTime;

	// The start time for this fuel.
	public int lastItemBurnTime;

	// How long time left before item is cooked.
	public int lastCookTime;

	public AbstractContainerGlow(InventoryPlayer inventory, AbstractTileEntityGlow tileEntity) {
		this.glowEntity = tileEntity;
		addSlots(inventory, tileEntity);
	}

	protected void addSlots(InventoryPlayer inv, AbstractTileEntityGlow entity) {
	}

	protected void addPlayerSlots(InventoryPlayer inv, AbstractTileEntityGlow entity) {
		// Adds the inventory to furnace's gui.
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Adds the hotbar slots to the gui.
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142)); // 198
		}
	}

	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.glowEntity.cookTime);
		craft.sendProgressBarUpdate(this, 1, this.glowEntity.burnTime);
		craft.sendProgressBarUpdate(this, 2, this.glowEntity.currentBurnTime);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.glowEntity.cookTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.glowEntity.cookTime);
			}

			if (this.lastBurnTime != this.glowEntity.burnTime) {
				icrafting.sendProgressBarUpdate(this, 1, this.glowEntity.burnTime);
			}

			if (this.lastItemBurnTime != this.glowEntity.currentBurnTime) {
				icrafting.sendProgressBarUpdate(this, 2, this.glowEntity.currentBurnTime);
			}
		}

		this.lastCookTime = this.glowEntity.cookTime;
		this.lastBurnTime = this.glowEntity.burnTime;
		this.lastItemBurnTime = this.glowEntity.currentBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int newVal) {
		if (slot == 0) this.glowEntity.cookTime = newVal;
		if (slot == 1) this.glowEntity.burnTime = newVal;
		if (slot == 2) this.glowEntity.currentBurnTime = newVal;
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

	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
		return super.mergeItemStack(stack, start, end, reverse);
	}

}
