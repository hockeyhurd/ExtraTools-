package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import com.hockeyhurd.entity.throwable.EntityPuck;
import com.hockeyhurd.mod.ExtraTools;

public class ItemHockeyStick extends ItemSword {

	public ItemHockeyStick(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("HockeyStick");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "HockeyStick");
	}

	/*@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(Item.snowball.itemID)) {
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(new EntitySnowball(par2World, par3EntityPlayer));
			}
		}
		return par1ItemStack;
	}*/

	// TODO: Change the throwing object to a custom made hockey puck! Ouch!
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World world, EntityPlayer player, int itemInUseCount) {
		final Item _item = ExtraTools.hockeyPuck;
		if (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(_item)) {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityPuck(world, player));
			}
		}
		
	}

}
