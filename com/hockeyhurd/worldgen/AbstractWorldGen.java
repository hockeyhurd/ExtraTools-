package com.hockeyhurd.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class AbstractWorldGen implements IWorldGenerator {

	private final int chunkSize = 16;
	protected final int chanceOfSpawn;
	// protected final int chanceOfSpawn_nether = chanceOfSpawn * chanceOfSpawn * 2;
	protected final int chanceOfSpawn_nether;
	protected final int minVeinSize, maxVeinSize;
	protected final int minY, maxY;
	protected final Block blockSpawn, blockSpawnNether;
	
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
	public AbstractWorldGen(Block blockSpawn, Block blockSpawnNether, int chanceofSpawn, int chanceOfSpawn_nether, int minVeinSize, int maxVeinSize, int minY, int maxY) {
		this.blockSpawn = blockSpawn;
		this.blockSpawnNether = blockSpawnNether;
		this.chanceOfSpawn = chanceofSpawn;
		this.chanceOfSpawn_nether = chanceOfSpawn_nether;
		this.minVeinSize = minVeinSize;
		this.maxVeinSize = maxVeinSize;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	/**
	 * Simpler method to main constructor. Handles just the spawning in the overworld so you don't have to handle nether stuff.
	 * @param blockSpawn
	 * @param chanceOfSpawn
	 * @param minVeinSize
	 * @param maxVeinSize
	 * @param minY
	 * @param maxY
	 */
	public AbstractWorldGen(Block blockSpawn, int chanceOfSpawn, int minVeinSize, int maxVeinSize, int minY, int maxY) {
		this(blockSpawn, null, chanceOfSpawn, -1, minVeinSize, maxVeinSize, minY, maxY);
	}
	
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
			case -1 : 
				generateNether(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 0 : 
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 1 :
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	/**
	 * handles ore generation in nether.
	 * @param world = world object.
	 * @param random = random chance object.
	 * @param blockX = block posX.
	 * @param blockZ = block posZ.
	 */
	protected void generateNether(World world, Random random, int blockX, int blockZ) {
		if (this.blockSpawnNether == null || this.chanceOfSpawn_nether <= 0) return;
		
		int veinSize = this.minVeinSize + random.nextInt(this.maxVeinSize - this.minVeinSize);
		addOreSpawn(this.blockSpawnNether, world, random, blockX, blockZ, chunkSize, chunkSize, veinSize, chanceOfSpawn_nether, this.minY, this.maxY);
	}

	/**
	 * handles ore generation in nether.
	 * @param world = world object.
	 * @param random = random chance object.
	 * @param blockX = block posX.
	 * @param blockZ = block posZ.
	 */
	protected void generateSurface(World world, Random random, int blockX, int blockZ) {
		if (this.blockSpawn == null || this.chanceOfSpawn <= 0) return;
		
		int veinSize = this.minVeinSize + random.nextInt(this.maxVeinSize - this.minVeinSize);
		addOreSpawn(this.blockSpawn, world, random, blockX, blockZ, chunkSize, chunkSize, veinSize, chanceOfSpawn, this.minY, this.maxY);
	}
	
	/**
	 * handles ore generation in nether.
	 * @param world = world object.
	 * @param random = random chance object.
	 * @param blockX = block posX.
	 * @param blockZ = block posZ.
	 */
	protected void generateEnd(World world, Random rand, int blockX, int blockZ) {
	}
	
	/**
	 * Handles all looping operation when actually generating the ore.
	 * @param block = block to spawn.
	 * @param world = world object.
	 * @param random = random object.
	 * @param blockXPos = block x-position.
	 * @param blockZPos  = block z-position.
	 * @param maxX = max x-position to spawn.
	 * @param maxZ = max z-position to spawn.
	 * @param maxVeinSize = max size of vein.
	 * @param chanceToSpawn = chance of spawning.
	 * @param minY = minimum y-level to spawn.
	 * @param maxY = maximum y-level to spawn.
	 */
	private void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY) {
		for (int i = 0; i < chanceToSpawn; i++) {
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = + blockZPos + random.nextInt(maxZ);
			
			(new WorldGenMinable(block, maxVeinSize)).generate(world, random, posX, posY, posZ);
		}
	}

}
