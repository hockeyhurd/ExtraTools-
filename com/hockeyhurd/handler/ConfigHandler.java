package com.hockeyhurd.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.ItemHelper;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	private FMLPreInitializationEvent event;
	private HashMap<String, Integer> map;
	private List<Block> blocks;
	private List<Item> items;
	private DefaultIDHandler dh;
	private BlockHelper bh;
	private ItemHelper ih;
	
	// Properties:
	public boolean easyRecipes = false;
	public boolean altFireStarRecipe = true;

	public ConfigHandler(FMLPreInitializationEvent event) {
		this.event = event;
		blocks = new ArrayList<Block>();
		items = new ArrayList<Item>();
		dh = new DefaultIDHandler();
		bh = new BlockHelper();
		ih = new ItemHelper();
	}

	public void handleConfiguration() {
		map = new HashMap<String, Integer>();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		easyRecipes = config.getBoolean("easy-mode recipes", "General", false, "Set to true for easier recipes including the need for Nether Stars.");
		altFireStarRecipe = config.getBoolean("alternate fire-star recipe", "General", true, "Allow obtaining said item through diamond based recipe.");
		
		config.save();

		// initLists();
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

	private void initLists() {
		for (int i = (Integer) map.values().toArray()[0]; i < map.values().size(); i++) {
			if (bh.blockListContains(i) && !ih.itemListContains(i)) this.blocks.add(bh.getBlock(i));
			else if (!bh.blockListContains(i) && ih.itemListContains(i)) this.items.add(ih.getItem(i));
		}
	}

	public List<Block> getRegisteredBlocks() {
		return this.blocks;
	}

	public List<Item> getRegisteredItems() {
		return this.items;
	}

}
