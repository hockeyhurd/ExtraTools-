package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import org.lwjgl.input.Keyboard;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.Waila;

public class ItemGlowHoe extends ItemHoe {

	public ItemGlowHoe(ToolMaterial toolGlow) {
		super(toolGlow);
		this.setUnlocalizedName("GlowHoeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);
	}

	public void registerIcons(IIconRegister reg) {
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
				waila.finder();
			}
			
			else {
				// Make sure the shiftClick paramater is set to false before performing said action!
				waila.setShiftClick(false);
				waila.finder();
			}
			
			UseHoeEvent event = new UseHoeEvent(entityPlayer, itemStack, world, x, y, z);
			MinecraftForge.EVENT_BUS.post(event);
			if (event.getResult() == cpw.mods.fml.common.eventhandler.Event.Result.ALLOW) return true;
			else if (event.getResult() == cpw.mods.fml.common.eventhandler.Event.Result.DENY) return false;
			
			return true;
		}
		
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Unbreakable!");
		list.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Shift + Right Click to till 3x3 area!");
	}

}
