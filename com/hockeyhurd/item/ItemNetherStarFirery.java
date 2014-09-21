package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.extratools.ExtraTools;

public class ItemNetherStarFirery extends Item {

	public ItemNetherStarFirery() {
		super();
		this.setUnlocalizedName("NetherStarFirery");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}
	
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "FireryNetherStar");
	}
	
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
