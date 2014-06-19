package com.hockeyhurd.main;

import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.launchwrapper.LogWrapper;

import com.hockeyhurd.creativetab.MyCreativeTab;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "ExtraTools+", name = "ExtraTools+", version = "v0.01")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.client.ClientProxy", serverSide = "com.hockeyhurd.main.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("ExtraTools+")
	public static ExtraTools instance;
	
	// Creative Tabs
	public static CreativeTabs myCreativeTab = new MyCreativeTab(CreativeTabs.getNextID(), "ExtraTools+");
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
	}
	
	public ExtraTools() {
		LogWrapper.log(Level.INFO, "ExtraTools+ loaded succesfully!");
	}
	
}
