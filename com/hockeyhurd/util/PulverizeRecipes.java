package com.hockeyhurd.util;

import static com.hockeyhurd.mod.ExtraTools.glowDust;
import static com.hockeyhurd.mod.ExtraTools.glowOre;
import static com.hockeyhurd.mod.ExtraTools.pulverizedGold;
import static com.hockeyhurd.mod.ExtraTools.pulverizedIron;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PulverizeRecipes {

	private static HashMap<Block, ItemStack> mapVanilla;
	private static HashMap<String, String> mapModded;
	private static Set<Entry<String, String>> mapSet;

	public PulverizeRecipes() {
	}

	public static void init() {
		mapVanilla = new HashMap<Block, ItemStack>();
		mapModded = new HashMap<String, String>();

		// Normal mapping
		put(glowOre, new ItemStack(glowDust, 2));
		put(Blocks.iron_ore, new ItemStack(pulverizedIron, 2));
		put(Blocks.gold_ore, new ItemStack(pulverizedGold, 2));

		// Fall back mapping
		mapModded.put("oreCopper", "dustCopper");
		mapModded.put("oreTin", "dustTin");
		mapModded.put("oreAluminium", "dustAluminium");
		mapModded.put("oreLead", "dustLead");
		mapModded.put("oreSilver", "dustSilver");

		initEntries();
	}

	private static void initEntries() {
		mapSet = mapModded.entrySet();
	}

	private static void put(Block block, ItemStack val) {
		mapVanilla.put(block, val);
	}

	public static HashMap<Block, ItemStack> getMap() {
		return mapVanilla;
	}

	public static ItemStack pulverizeList(ItemStack stack) {
		boolean flag = false;
		ItemStack temp = null;

		for (Block block : mapVanilla.keySet()) {
			if (stack.getItem() == Item.getItemFromBlock(block)) {
				temp = mapVanilla.get(block);
				flag = true;
				break;
			}
		}

		if (flag && temp != null) return temp;

		int currentID = OreDictionary.getOreID(stack);
		String current = "", current2 = "";
		for (int i = 0; i < OreDictionary.getOreNames().length; i++) {
			for (Entry<String, String> s : mapSet) {
				current = s.getKey();
				current2 = s.getValue();
				int id = OreDictionary.getOreID(current);
				
				if (current.equals(OreDictionary.getOreNames()[i]) && currentID == id) {
					flag = true;
					break;
				}
			}

			if (flag) {
				Block block = Block.getBlockById(OreDictionary.getOreID(current));
				temp = OreDictionary.getOres(current2).get(0);
				temp.stackSize = 2;
				break;
			}
		}

		return flag && temp != null ? temp : (ItemStack) null;
	}

}
