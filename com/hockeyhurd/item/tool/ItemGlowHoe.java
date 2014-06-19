package com.hockeyhurd.item.tool;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import org.lwjgl.input.Keyboard;

import com.hockeyhurd.main.ExtraTools;
import com.hockeyhurd.util.Waila;

public class ItemGlowHoe extends ItemHoe {

	public ItemGlowHoe(int id, EnumToolMaterial toolGlow) {
		super(id, toolGlow);
		this.setUnlocalizedName("GlowHoeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowHoeUnbreakable");
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (!entityPlayer.canPlayerEdit(x, y, z, par7, itemStack)) return false;
		else {
			
			Waila waila = new Waila(itemStack, world, entityPlayer, null, false, false);
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				
				// Make sure the shiftClick paramater is set to true before performing said action!
				waila.setShiftClick(true);
				waila.getBlockLookingAt();
			}
			
			else {
				// Make sure the shiftClick paramater is set to false before performing said action!
				waila.setShiftClick(false);
				waila.getBlockLookingAt();
			}
			
			UseHoeEvent event = new UseHoeEvent(entityPlayer, itemStack, world, x, y, z);
			MinecraftForge.EVENT_BUS.post(event);
			if (event.getResult() == Result.ALLOW) return true;
			else if (event.getResult() == Result.DENY) return false;
			
			return true;
		}
		
	}

}
