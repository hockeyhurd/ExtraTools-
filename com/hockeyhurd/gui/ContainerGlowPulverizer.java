package com.hockeyhurd.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import com.hockeyhurd.entity.tileentity.AbstractTileEntityGlow;
import com.hockeyhurd.entity.tileentity.TileEntityGlowPulverizer;

public class ContainerGlowPulverizer extends AbstractContainerGlow {

	public ContainerGlowPulverizer(InventoryPlayer inventory, TileEntityGlowPulverizer tileEntity) {
		super(inventory, tileEntity);
		this.glowEntity = tileEntity;
		addSlots(inventory, tileEntity);
	}

	protected void addSlots(InventoryPlayer inv, TileEntityGlowPulverizer entity) {
		// Slot 1, (the entity, id, location x, location y).
		this.addSlotToContainer(new Slot(entity, 0, 56, 17));
		this.addSlotToContainer(new Slot(entity, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(inv.player, entity, 2, 116, 35));
		
		addPlayerSlots(inv, entity);
	}

}
