package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.entity.throwable.EntityPuck;
import com.hockeyhurd.mod.ExtraTools;

public class ItemHockeyPuck extends Item {

	public ItemHockeyPuck() {
		super();
		this.setUnlocalizedName("HockeyPuck");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "HockeyPuck");
	}
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityPuck(world, player));
        }

        return stack;
    }

}
