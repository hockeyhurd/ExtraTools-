package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemDebugger extends Item {

	private TimerHelper th;

	public ItemDebugger(int id) {
		super(id);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setUnlocalizedName("ItemDebugger");
		this.th = new TimerHelper(20);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "ItemDebugger");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (th.use) return stack;

		Waila waila = new Waila(stack, world, player, null, false, false);
		if (!th.use) waila.finder();
		th.setUse(true);

		return stack;
	}

	// When player mines a block, mine a 3x3 area.
	public boolean onBlockDestroyed(ItemStack stack, World world, int par3, int x, int y, int z, EntityLivingBase entity) {
		// Don't allow the player to break anything!
		return false;
	}

}
