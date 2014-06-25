/*
 * Class is used to currently search through any given chunk and determine
 * if a certain block is present and of how much there is.
 */

package com.hockeyhurd.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkHelper {

	private World world;
	private EntityPlayer player;
	
	public ChunkHelper(World world, EntityPlayer player) {
		this.world = world;
		this.player = player;
	}
	
	// Searches chunk for a block.
	public void searchChunk(Block blockToFind) {
		// Make sure I didn't derp up anything and the block to be searched for is an actual block.
		if (blockToFind == null || blockToFind.blockID <= 0) {
			System.err.println("Block to find is not a block!");
			return;
		}
		int xPos = (int) player.posX;
		int zPos = (int) player.posZ;
		Chunk chunk = world.getChunkFromBlockCoords(xPos, zPos);
		List<Block> list = new ArrayList<Block>();
		
		int chunkX = chunk.xPosition * 16;
		int chunkZ = chunk.zPosition * 16;
		
		// Search through the chunk through 3-Dimensions and getting each block to be ananlyzed.
		for (int y = (int) player.posY; y > 0; y--) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					
					// Get the block id of the block being analyzed,
					int blockID = world.getBlockId(chunkX + x, y, chunkZ + z);
					// If the block id is not of 'air' and it matches the desired block, add it to the list.
					if (blockID > 0 && blockID == blockToFind.blockID) {
						Block block = blockToFind;
						list.add(block);
						// System.out.println("Diamond added at pos: (" + x + ", " + y + ", " + z + ").");
					}
					
					else continue;
				}
			}
		}
		
		// Print out to the player how much of the given block is currently in the chunk they are standing in.
		int amount = list.size();
		BlockHelper blockHelper = new BlockHelper(world, player);
		player.sendChatToPlayer(ChatMessageComponent.createFromText(amount + " " + blockHelper.getLocalized(blockToFind) + "(s) diamonds left to be found!"));
		
		// Make sure the list removed from memory.
		list.removeAll(Collections.EMPTY_LIST);
		
	}
	
}
