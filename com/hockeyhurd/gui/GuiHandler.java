package com.hockeyhurd.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch (id) {
			case 0:
				return new ContainerGlowFurnace(player.inventory, (TileEntityGlowFurnace) tile_entity);
			case 1:
				return new ContainerGlowChest(player.inventory, (TileEntityGlowChest) tile_entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);
		switch (id) {
			case 0:
				return new GuiGlowFurnace(player.inventory, (TileEntityGlowFurnace) tile_entity);
			case 1:
				return new ContainerGlowChest(player.inventory, (TileEntityGlowChest) tile_entity);
		}
		return null;
	}
}
