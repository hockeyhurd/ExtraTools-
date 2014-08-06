package com.hockeyhurd.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerGlowChest implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch (id) {
			case 0:
				return new ContainerGlowChest(player.inventory, (TileEntityGlowChest) tile_entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch (id) {
			case 0:
				return new ContainerGlowChest(player.inventory, (TileEntityGlowChest) tile_entity);
		}
		return null;
	}

}
