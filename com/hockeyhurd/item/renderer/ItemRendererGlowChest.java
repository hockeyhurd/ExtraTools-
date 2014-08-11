package com.hockeyhurd.item.renderer;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererGlowChest implements IItemRenderer {

	private ModelChest chestModel;
	
	public ItemRendererGlowChest() {
	}
	
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		return true;
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityGlowChest(), 0.0d, 0.0d, 0.0d, 0.0f);
	}

}
