package com.hockeyhurd.handler;

import java.util.HashMap;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	private FMLPreInitializationEvent event;
	private HashMap<String, Integer> map;
	private DefaultIDHandler dh;
	
	public ConfigHandler(FMLPreInitializationEvent event) {
		this.event = event;
		dh = new DefaultIDHandler();
	}
	
	public void handleConfiguration() {
		map = new HashMap<String, Integer>();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		int glowRockID = config.getBlock("GlowRock", dh.getNextAvailableID()).getInt();
		add("glowRockID", glowRockID);
		
		int glowTorchID = config.getBlock("GlowTorch", dh.getNextAvailableID()).getInt();
		add("glowTorchID", glowTorchID);
		
		int glowOreID = config.getBlock("GlowOre", dh.getNextAvailableID()).getInt();
		add("glowOreID", glowOreID);
		
		int glowDustID = config.getItem("GlowDust", dh.getNextAvailableID()).getInt();
		add("glowDustID", glowDustID);
		
		int glowIngotID = config.getItem("GlowIngot", dh.getNextAvailableID()).getInt();
		add("glowIngotID", glowIngotID);
		
		int diamondFusedNetherStarID = config.getItem("DiamondFusedNetherStar", dh.getNextAvailableID()).getInt();
		add("diamondFusedNetherStarID", diamondFusedNetherStarID);
		
		int netherSoulCollectorID = config.getItem("NetherSoulCollector", dh.getNextAvailableID()).getInt();
		add("netherSoulCollectorID", netherSoulCollectorID);
		
		int glowPickaxeUnbreakableID = config.getItem("GlowPickaxeUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowPickaxeUnbreakableID", glowPickaxeUnbreakableID);
		
		int glowHoeUnbreakableID = config.getItem("GlowHoeUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowHoeUnbreakableID", glowHoeUnbreakableID);
		
		// TODO: Finish the config
		
	}
	
	public int getID(String name) {
		String addID = "ID";
		name += addID;
		return map.get(name);
	}
	
	private void add(String name, int id) {
		map.put(name, id);
	}
	
}
