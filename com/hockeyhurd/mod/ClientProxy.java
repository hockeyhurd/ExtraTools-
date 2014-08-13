package com.hockeyhurd.mod;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.entity.tileentity.renderer.TileEntityGlowChestRenderer;
import com.hockeyhurd.handler.EventHookContainer;
import com.hockeyhurd.handler.KeyEventHandler;
import com.hockeyhurd.handler.PacketHandler;
import com.hockeyhurd.item.renderer.ItemRendererGlowChest;
import static com.hockeyhurd.mod.ExtraTools.*;
import com.hockeyhurd.util.Keybindings;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public ClientProxy() {
	}
	
	protected void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EventHookContainer());
		PacketHandler.init();
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
	}
}
