package com.hockeyhurd.block.ores;

import net.minecraft.block.material.Material;

public class BlockGlowOre extends AbstractBlockOre {

	public BlockGlowOre(Material material, String name) {
		super(material, name);
		this.setLightLevel(0.3f);
		this.setResistance(5);
	}

}
