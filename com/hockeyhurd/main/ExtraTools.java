package com.hockeyhurd.main;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.launchwrapper.LogWrapper;

import com.hockeyhurd.block.BlockGlowRock;
import com.hockeyhurd.block.ores.BlockGlowOre;
import com.hockeyhurd.creativetab.MyCreativeTab;
import com.hockeyhurd.handler.DefaultIDHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ExtraTools+", name = "ExtraTools+", version = "v0.01")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.client.ClientProxy", serverSide = "com.hockeyhurd.main.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("ExtraTools+")
	public static ExtraTools instance;
	
	public static String modPrefix = "extratools:";
	
	// Blocks
	public static Block glowRock = new BlockGlowRock(DefaultIDHandler.getNextAvailableID(), Material.glass);
	
	// Ores
	public static Block glowOre = new BlockGlowOre(DefaultIDHandler.getNextAvailableID(), Material.rock);
	
	// Creative Tabs
	public static CreativeTabs myCreativeTab = new MyCreativeTab(CreativeTabs.getNextID(), "ExtraTools+");
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
	}
	
	public ExtraTools() {
		registerBlocks();
		addLocalizedNames();
		
		LogWrapper.log(Level.INFO, "ExtraTools+ loaded succesfully!");
	}

	private void registerBlocks() {
		GameRegistry.registerBlock(glowRock, "GlowRock");
		GameRegistry.registerBlock(glowOre, "GlowOre");
	}
	
	private void addLocalizedNames() {
		LanguageRegistry.addName(glowRock, "Glow Rock");
		LanguageRegistry.addName(glowOre, "Glow Ore");
	}
	
}
