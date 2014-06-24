package com.hockeyhurd.handler;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.hockeyhurd.main.ExtraTools;

public class EventHookContainer {

	private final Item helm = ExtraTools.glowHelmet;
	private final Item chest = ExtraTools.glowChestplate;
	private final Item leg = ExtraTools.glowLegging;
	private final Item boot = ExtraTools.glowBoot;
	
	private boolean bootCheck = false;
	private boolean legCheck = false;
	private boolean chestCheck = false;
	private boolean helmCheck = false;

	// Removed as unused.
	/*@ForgeSubscribe
	public void onPlayerDamage(LivingHurtEvent event) {
		if (!(event.entityLiving instanceof EntityPlayer)) return;
		else {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
		}
	}*/
	
	@ForgeSubscribe 
	public void onOreBreak(BreakEvent event) {
		if (event.block.blockID == ExtraTools.glowOre.blockID) {
			World world = event.getPlayer().worldObj;
			Random random = new Random();
			int chance = 15;
			
			int val = 1 + random.nextInt(99);
			if (val <= chance) world.spawnEntityInWorld(new EntityItem(world, (double) event.x, (double) event.y, (double) event.z, new ItemStack(ExtraTools.glowDust)));
		}
		
		else return;
	}

	@ForgeSubscribe
	public void onPlayerUpdate(LivingUpdateEvent event) {
		
		if (!(event.entityLiving instanceof EntityPlayer)) return;
		else {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			Item currentHelm = null;
			Item currentChest = null;
			Item currentLeg = null;
			Item currentBoot = null;

			if (player.getCurrentArmor(0) != null) {
				currentBoot = player.getCurrentArmor(0).getItem();
			}

			if (player.getCurrentArmor(1) != null) {
				currentLeg = player.getCurrentArmor(1).getItem();
			}

			if (player.getCurrentArmor(2) != null) {
				currentChest = player.getCurrentArmor(2).getItem();
			}

			if (player.getCurrentArmor(3) != null) {
				currentHelm = player.getCurrentArmor(3).getItem();
			}

			/*
			 * Checks if the user removes any part(s) of the armor set and if already airborne revove flying ability and make them fall! Ouch!
			 */
			else {
				bootCheck = legCheck = chestCheck = helmCheck = false;
				if (player.isAirBorne) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
				return;
			}

			if (currentBoot == boot) {
				bootCheck = true;
				if (!player.isCollidedVertically) player.fallDistance = 0f;
			}

			if (currentLeg == leg) {
				legCheck = true;
				// player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2, 0));
			}

			if (currentChest == chest) {
				chestCheck = true;
				if (!player.isBurning()) player.removePotionEffect(Potion.fireResistance.id);
				else player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 5, 0));
			}

			if (currentHelm == helm) {
				helmCheck = true;
				if (player.isInWater()) player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 5, 0));
				else player.removePotionEffect(Potion.waterBreathing.id);
			}
			
			if (bootCheck && legCheck && chestCheck && helmCheck) player.capabilities.allowFlying = true;			
			else player.capabilities.allowFlying = false;

		}

	}

	/*
	 * Event called when user hovers over my items at the end of the <List>.
	 */
	@ForgeSubscribe
	public void onItemHover(ItemTooltipEvent event) {
		int currentID = event.itemStack.itemID;
		int pickID = new ItemStack(ExtraTools.glowPickaxeUnbreakable, 1).itemID;
		int swordID = new ItemStack(ExtraTools.glowSwordUnbreakable, 1).itemID;
		int axeID = new ItemStack(ExtraTools.glowAxeUnbreakable, 1).itemID;
		int hoeID = new ItemStack(ExtraTools.glowHoeUnbreakable, 1).itemID;
		int shovelID = new ItemStack(ExtraTools.glowShovelUnbreakable, 1).itemID;
		// int netherSoulCollectorID = new ItemStack(ExtraTools.netherSoulCollector, 1).itemID;

		if (currentID == pickID || currentID == swordID || currentID == axeID || currentID == hoeID || currentID == shovelID) {
			event.toolTip.add("Unbreakable!");
			if (currentID == pickID) event.toolTip.add("Right click to place torches");
		}
		
		else return;
	}

	// Future possible commands
	/*
	 * @ForgeSubscribe public void onPlayerChat(ServerChatEvent event) { }
	 */
	
}
