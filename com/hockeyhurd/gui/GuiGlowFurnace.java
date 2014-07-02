package com.hockeyhurd.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.main.ExtraTools;

public class GuiGlowFurnace extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation("extratools", "textures/gui/GuiGlowFurnace.png");
	
	public TileEntityGlowFurnace glowFurnace;
	
	public GuiGlowFurnace(InventoryPlayer inv, TileEntityGlowFurnace entity) {
		super(new ContainerGlowFurnace(inv, entity));
		
		this.glowFurnace = entity;
		this.xSize = 176;
		this.ySize = 166;
	}

	public void drawGuiContainerForgroundLayer(int x, int y) {
		String name = this.glowFurnace.isInvNameLocalized() ? this.glowFurnace.getInvName() : I18n.getString(this.glowFurnace.getInvName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	
}