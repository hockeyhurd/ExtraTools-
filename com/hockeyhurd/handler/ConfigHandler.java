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
		
		loadID(config);
		
		config.save();
	}
	
	private void loadID(Configuration config) {
		int glowRockID = config.getBlock("GlowRock", dh.getNextAvailableID() ).getInt();
		add("glowRockID", glowRockID);
		
		int glowTorchID = config.getBlock("GlowTorch", dh.getNextAvailableID()).getInt();
		add("glowTorchID", glowTorchID);
		
		int glowIngotBlockID = config.getBlock("GlowIngotBlock", dh.getNextAvailableID()).getInt();
		add("glowIngotBlockID", glowIngotBlockID);
		
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
		
		int fireryNetherStarID = config.getItem("FireryNetherStar", dh.getNextAvailableID()).getInt();
		add("fireryNetherStarID", fireryNetherStarID);
		
		int glowCoalID = config.getItem("GlowCoal", dh.getNextAvailableID()).getInt();
		add("glowCoalID", glowCoalID);
		
		int glowPickaxeUnbreakableID = config.getItem("GlowPickaxeUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowPickaxeUnbreakableID", glowPickaxeUnbreakableID);
		
		int glowHoeUnbreakableID = config.getItem("GlowHoeUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowHoeUnbreakableID", glowHoeUnbreakableID);
		
		int glowSwordUnbreakableID = config.getItem("GlowSwordUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowSwordUnbreakableID", glowSwordUnbreakableID);
		
		int glowAxeUnbreakableID = config.getItem("GlowAxeUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowAxeUnbreakableID", glowAxeUnbreakableID);
		
		int glowShovelUnbreakableID = config.getItem("GlowShovelUnbreakable", dh.getNextAvailableID()).getInt();
		add("glowShovelUnbreakableID", glowShovelUnbreakableID);
		
		int glowHelmetID = config.getItem("GlowHelmetID", dh.getNextAvailableID()).getInt();
		add("glowHelmetID", glowHelmetID);
		
		int glowChestplateID = config.getItem("GlowChestplateID", dh.getNextAvailableID()).getInt();
		add("glowChestplateID", glowChestplateID);
		
		int glowLeggingID = config.getItem("GlowLeggingID", dh.getNextAvailableID()).getInt();
		add("glowLeggingID", glowLeggingID);
		
		int glowBootID = config.getItem("GlowBootID", dh.getNextAvailableID()).getInt();
		add("glowBootID", glowBootID);
		
		int hockeyStickID = config.getItem("HockeStickID", dh.getNextAvailableID()).getInt();
		add("hockeyStickID", hockeyStickID);
	}
	
	public int getID(String name) {
		if (name == null || name.equals("")) {
			System.err.println("Name: " + name + " is null! Please make sure you are calling the correct item/block to get its ID!");
			return -1;
		}
		String addID = "ID";
		name += addID;
		return map.get(name);
	}
	
	private void add(String name, int id) {
		map.put(name, id);
	}
	
}
