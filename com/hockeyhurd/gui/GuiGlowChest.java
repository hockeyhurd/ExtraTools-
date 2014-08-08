package com.hockeyhurd.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;

public class GuiGlowChest extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation("extratools", "textures/gui/GuiGlowChest.png");
	// public static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
	
	public TileEntityGlowChest glowChest;
	
	public GuiGlowChest(InventoryPlayer inv, TileEntityGlowChest entity) {
		super(new ContainerGlowChest(inv, entity));
		
		this.glowChest = entity;
		this.xSize = 176;
		this.ySize = 241; // 277;
	}

	public void drawGuiContainerForegroundLayer(int x, int y) {
		String name = this.glowChest.hasCustomInventoryName() ? this.glowChest.getInventoryName() : I18n.format(this.glowChest.getInventoryName(), new Object[0]);
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
