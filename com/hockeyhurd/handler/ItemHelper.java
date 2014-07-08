package com.hockeyhurd.handler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemHelper {

	private World world;
	private EntityPlayer player;

	public ItemHelper(World world, EntityPlayer player) {
		this.world = world;
		this.player = player;
	}

	// Only use this constructor is there is no need with world or player interaction!
	public ItemHelper() {

	}

	public Item getItem(int id) {
		return id > 0 ? Item.itemsList[id] : null;
	}

	public String getUnlocalizedName(Item item) {
		return item != null ? item.getUnlocalizedName() : "This is not an item!";
	}

	public boolean itemListContains(int id) {
		Item item_ = Item.itemsList[Item.itemsList.length - 1];

		// Checks if the given id is > the last registered block and if so, just return false;
		if (item_ != null && id > item_.itemID) return false;
		Item item = null;

		for (int i = 0; i < Item.itemsList.length; i++) {
			if (Item.itemsList[i] != null && Item.itemsList[i].itemID == id) {
				item = Item.itemsList[i];
				break;
			}
		}

		return item != null ? true : false;
	}

	public boolean isAnItem(Item item) {
		return itemListContains(item.itemID);
	}

}
