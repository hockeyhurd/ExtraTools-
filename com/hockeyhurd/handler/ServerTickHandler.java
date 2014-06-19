package com.hockeyhurd.handler;

/* Credit to SkylordJoel for base code
 * for me to tweak for my mods needs.
 */

import java.util.EnumSet;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler {

	private EnumSet<TickType> ticksToGet;
	
	public void PlayerTickHandler(EnumSet<TickType> ticksToGet) {
		this.ticksToGet = ticksToGet;
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.PLAYER))) {
			onPlayerTick((EntityPlayer)tickData[0], null);
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER, TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return null;
	}
	
	private void onPlayerTick(EntityPlayer player, World world) {
		if ((player.getCurrentArmor(3) != null) && (player.getCurrentArmor(2) != null) && (player.getCurrentArmor(1) != null) && (player.getCurrentArmor(0) != null)) {
			ItemStack helm = player.getCurrentArmor(3);
			ItemStack chest = player.getCurrentArmor(2);
			ItemStack legs = player.getCurrentArmor(1);
			ItemStack boot = player.getCurrentArmor(0);
			
			if (helm.getItem() == ExtraTools.glowHelmet && chest.getItem() == ExtraTools.glowChestplate && legs.getItem() == ExtraTools.glowLegging && boot.getItem() == ExtraTools.glowBoot) {
				player.capabilities.allowFlying = true;
				player.fallDistance = 0.0f;
			}
		}
		
		else if (!player.capabilities.isCreativeMode) {
			player.capabilities.allowFlying = false;
		}
	}

}

