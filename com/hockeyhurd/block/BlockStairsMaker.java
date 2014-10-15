package com.hockeyhurd.block;

import com.hockeyhurd.extratools.ExtraTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockStairsMaker extends BlockStairs {

	public BlockStairsMaker(Block block, int id) {
		super(block, id);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setBlockName(getName(block) + "Stairs");
		this.setHardness(1.5f);
	}
	
	private String getName(Block block) {
		String rawName = block.getLocalizedName();
		String newName = "";
		boolean flag = false;
		for (char c : rawName.toCharArray()) {
			if (c != ' ' && c != '.') {
				if (flag) c = String.valueOf(c).toUpperCase().charAt(0);
				newName += c;
				flag = false;
			}
			else flag = true;
		}
		
		return newName;
	}

}
