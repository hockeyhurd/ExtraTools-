package com.hockeyhurd.extratools;

import static com.hockeyhurd.extratools.ExtraTools.glowChest;
import static com.hockeyhurd.extratools.ExtraTools.glowRock;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.hockeyhurd.block.renderer.GlowRockRenderer;
import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.entity.tileentity.renderer.TileEntityGlowChestRenderer;
import com.hockeyhurd.handler.KeyEventHandler;
import com.hockeyhurd.item.renderer.ItemRendererGlowChest;
import com.hockeyhurd.item.renderer.ItemRendererGlowRock;
import com.hockeyhurd.util.Keybindings;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public static int renderPass;
	public static int glowRockRenderType;
	
	public ClientProxy() {
	}
	
	protected void registerEventHandlers() {
		// MinecraftForge.EVENT_BUS.register(new EventHookContainer());
		// PacketHandler.init();
		super.registerEventHandlers();
		 // if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			Keybindings.init();
			FMLCommonHandler.instance().bus().register(new KeyEventHandler());
		 // }
	}

	public void registerRenderInformation() {
		registerSpecialRenderers();
	}

	private void registerSpecialRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlowChest.class, new TileEntityGlowChestRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(glowChest), new ItemRendererGlowChest());
		
		glowRockRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new GlowRockRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(glowRock), new ItemRendererGlowRock());
	}
}
