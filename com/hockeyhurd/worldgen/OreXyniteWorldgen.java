package com.hockeyhurd.worldgen;

import net.minecraft.block.Block;

public class OreXyniteWorldgen extends AbstractWorldGen {

	/**
	 * Init chance of spawn. NOTE: set int to -1 if not spawning in said dimension, likewise the said block can be set to null.
	 * @param blockSpawn = Block to spawn.
	 * @param blockSpawnNether = block to spawn in nether.
	 * @param chanceofSpawn = Chance of spawning rate.
	 * @param chanceOfSpawn_nether = Chance of spawning rate.
	 * @param minVeinSize = min. size of given vein.
	 * @param maxVeinSize = max. size of given vein.
	 * @param minY = min. y-level.
	 * @param maxY = max. y-level.
	 */
	public OreXyniteWorldgen(Block blockSpawn, Block blockSpawnNether, int chanceofSpawn, int chanceOfSpawn_nether, int minVeinSize, int maxVeinSize, int minY, int maxY) {
		super(blockSpawn, blockSpawnNether, chanceofSpawn, chanceOfSpawn_nether, minVeinSize, maxVeinSize, minY, maxY);
	}

}
