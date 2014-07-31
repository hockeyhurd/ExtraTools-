package com.hockeyhurd.item.tool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.EntitySpawnerHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Vector3IHelper;
import com.hockeyhurd.util.Waila;

public class ItemWrench extends Item {

	private TimerHelper th;
	private EntitySpawnerHelper esh;
	private Block[] wrenchables;
	
	public ItemWrench() {
		super();
		this.setUnlocalizedName("Wrench");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		
		th = new TimerHelper();
		esh = new EntitySpawnerHelper();
		esh.init();
		this.wrenchables = ExtraTools.ch.getBlockWrenchArray();
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "Wrench");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!player.isSneaking()) return stack;
		
		if (!world.isRemote) {
			BlockHelper bh = new BlockHelper(world, player);
			Waila waila = new Waila(stack, world, player, null, false, false);
			if (!th.getUse() || th.excuser()) {
				waila.finder(false);
				Vector3IHelper vec = waila.getVector3I();
				int metaData = 0;
				
				if (bh.blockExists(vec)) {
					boolean contains = false;
					Block currentBlock = bh.getBlock(vec);
					if (currentBlock == Blocks.mob_spawner && world.getTileEntity(vec.getX(), vec.getY(), vec.getZ()) instanceof TileEntityMobSpawner) {
						contains = true;
						TileEntityMobSpawner te = (TileEntityMobSpawner) world.getTileEntity(vec.getX(), vec.getY(), vec.getZ());
						String entityToSpawn = te.func_145881_a().getEntityNameToSpawn();
						metaData = esh.getMappedID(entityToSpawn);
					}
					
					for (int i = 0; i < wrenchables.length; i++) {
						if ((wrenchables[i] != null && wrenchables[i] == currentBlock) || currentBlock == Blocks.mob_spawner) {
							contains = true;
							break;
						}
					}
					
					if (contains) {
						world.spawnEntityInWorld(new EntityItem(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(currentBlock, 1, metaData > 0 ? metaData : 0)));
						bh.destroyBlock(vec);
					}
				}
				
			}
			
			th.setUse(true);
		}
		
		return stack;
	}

}
