package com.hockeyhurd.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.hockeyhurd.entity.tileentity.TileEntityGlowPulverizer;

public class GuiGlowPulverizer extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation("extratools", "textures/gui/GuiGlowFurnace.png");
	public TileEntityGlowPulverizer te;
	
	public GuiGlowPulverizer(InventoryPlayer inv, TileEntityGlowPulverizer entity) {
		super(new ContainerGlowPulverizer(inv, entity));
		this.te = entity;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	public void drawGuiContainerForegroundLayer(int x, int y) {
		String name = this.te.hasCustomInventoryName() ? this.te.getInventoryName() : I18n.format(this.te.getInventoryName(), new Object[0]);
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if (this.te.isBurning()) {
			int i1 = this.te.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.te.getCookProgressScaled(24);
            this.drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, i1 + 1, 16);
		}
	}

}
