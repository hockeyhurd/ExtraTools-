package com.hockeyhurd.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGlowFurnace extends Container {

	private TileEntityGlowFurnace glowFurnace;

	// Time left for this furnace to burn for.
	public int lastBurnTime;

	// The start time for this fuel.
	public int lastItemBurnTime;

	// How long time left before item is cooked.
	public int lastCookTime;

	public ContainerGlowFurnace(InventoryPlayer inv, TileEntityGlowFurnace entity) {
		this.glowFurnace = entity;
		addSlots(inv, entity);
	}

	private void addSlots(InventoryPlayer inv, TileEntityGlowFurnace entity) {
		// Slot 1, (the entity, id, location x, location y).
		this.addSlotToContainer(new Slot(entity, 0, 56, 17));
		this.addSlotToContainer(new Slot(entity, 1, 56, 53));

		this.addSlotToContainer(new SlotFurnace(inv.player, entity, 2, 116, 35));

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
		craft.sendProgressBarUpdate(this, 0, this.glowFurnace.cookTime);
		craft.sendProgressBarUpdate(this, 1, this.glowFurnace.burnTime);
		craft.sendProgressBarUpdate(this, 2, this.glowFurnace.currentBurnTime);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.glowFurnace.cookTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.glowFurnace.cookTime);
			}

			if (this.lastBurnTime != this.glowFurnace.burnTime) {
				icrafting.sendProgressBarUpdate(this, 1, this.glowFurnace.burnTime);
			}

			if (this.lastItemBurnTime != this.glowFurnace.currentBurnTime) {
				icrafting.sendProgressBarUpdate(this, 2, this.glowFurnace.currentBurnTime);
			}
		}

		this.lastCookTime = this.glowFurnace.cookTime;
		this.lastBurnTime = this.glowFurnace.burnTime;
		this.lastItemBurnTime = this.glowFurnace.currentBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int newVal) {
		if (slot == 0) this.glowFurnace.cookTime = newVal;
		if (slot == 1) this.glowFurnace.burnTime = newVal;
		if (slot == 2) this.glowFurnace.currentBurnTime = newVal;
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
		// return this.glowFurnace.isUseableByPlayer(player);
		return true; // Let's make sure the player can always interact with this tileentity
	}

	public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
		return super.mergeItemStack(stack, start, end, reverse);
	}

}
