package com.hockeyhurd.util;

import java.util.HashMap;

import static com.hockeyhurd.mod.ExtraTools.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PulverizeRecipes {

	private static HashMap<Block, ItemStack> map;
	
	public PulverizeRecipes() {
	}
	
	public static void init() {
		map = new HashMap<Block, ItemStack>();
		put(Blocks.iron_ore, new ItemStack(pulverizedIron, 2));
	}
	
	private static void put(Block block, ItemStack val) {
		map.put(block, val);
	}
	
	public static ItemStack pulverizeList(ItemStack stack) {
		boolean flag = false;
		ItemStack temp = null;
		
		for (Block block : map.keySet()) {
			if (stack.getItem() == Item.getItemFromBlock(block)) {
				temp = map.get(block);
				flag = true;
				break;
			}
		}
		
		return flag && temp != null ? temp : (ItemStack) null;
	}

}
