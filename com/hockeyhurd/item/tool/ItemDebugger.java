package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.ChatHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;
import com.hockeyhurd.util.math.Vector4Helper;

public class ItemDebugger extends Item {

	private TimerHelper th;
	private ChatHelper ch;
	
	public ItemDebugger() {
		super();
		this.setUnlocalizedName("ItemDebugger");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		
		this.th = new TimerHelper();
		this.ch = new ChatHelper();
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Items.stick.getIcon(new ItemStack(Items.stick, 1), 0).getIconName());
	}
	
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && !th.use) {
			Waila waila = new Waila(stack, world, player, null, false, false);
			waila.finder(false);
			Vector4Helper vec = waila.getVector3I();
			BlockHelper bh = new BlockHelper(world, player);
			Block block = bh.getBlock(vec);
			
			if (ExtraTools.ch.debugMode) System.out.println("Block: " + block);
			player.addChatComponentMessage(ch.comp("Block: " + block.getLocalizedName()));
			player.addChatComponentMessage(ch.comp("SideHit: " + vec.getSideHit()));
		}
		return stack;
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Creative mode item only used for debugging!");
	}

}
