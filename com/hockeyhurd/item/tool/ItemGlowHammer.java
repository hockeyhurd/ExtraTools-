package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemGlowHammer extends ItemPickaxe {

	private TimerHelper th;

	public ItemGlowHammer(int id, EnumToolMaterial material) {
		super(id, material);
		this.setUnlocalizedName("GlowHammer");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		th = new TimerHelper(5);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowHammer");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public boolean onBlockDestroyed(ItemStack stack, World world, int par3, int x, int y, int z, EntityLivingBase entityLiving) {
		
		EntityPlayer player = (EntityPlayer) entityLiving;
		
		// If the player is sneaking void 3x3 mining,
		if (player.isSneaking()) return true;
		
		Waila waila = new Waila(stack, world, player, new BlockHelper(world, player).getBlock(x, y, z), false, false);
		waila.setOffset(1);

		if (!world.isRemote && (!th.use || th.excuser())) {
			waila.finder();
			th.setUse(true);
		}
		
		return true;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Mines a 3x3 area");
	}

}
