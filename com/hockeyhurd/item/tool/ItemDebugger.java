package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.ChatHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemDebugger extends Item {

	private TimerHelper th;

	public ItemDebugger(int id) {
		super(id);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setUnlocalizedName("ItemDebugger");
		this.setMaxStackSize(1);
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
	
	// Prevent player from being able to break any block with this item!
	public boolean onBlockStartBreak(ItemStack stack, int X, int Y, int Z, EntityPlayer player) {
		if (!th.use) {
			player.sendChatToPlayer(new ChatHelper().comp("Cannot break with this item!", EnumChatFormatting.DARK_RED));
			th.setUse(true);
		}
		
		return true;
	}

}
