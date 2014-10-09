package com.hockeyhurd.handler;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.extratools.ExtraTools;
import com.hockeyhurd.util.ChatHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHookContainer {

	private final Item helm = ExtraTools.glowHelmet;
	private final Item chest = ExtraTools.glowChestplate;
	private final Item leg = ExtraTools.glowLegging;
	private final Item boot = ExtraTools.glowBoot;

	private boolean bootCheck = false;
	private boolean legCheck = false;
	private boolean chestCheck = false;
	private boolean helmCheck = false;
	private boolean canFly = false;

	private final float glowCalcTime = ((float) TileEntityGlowFurnace.scaledTime / (float) TileEntityGlowFurnace.defaultCookTime) * 100;

	public EventHookContainer() {
	}

	private void setAllowedFly(boolean canFly) {
		this.canFly = canFly;
	}

	private boolean getAllowedFly() {
		return this.canFly;
	}

	@SubscribeEvent
	public void onOreBreak(BreakEvent event) {
		World world = event.getPlayer().worldObj;
		Random random = new Random();
		int chance = 15;
		int val = 1 + random.nextInt(99);
		
		if (event.block == ExtraTools.glowOre || event.block == ExtraTools.glowOreNether) {
			if (val <= chance) world.spawnEntityInWorld(new EntityItem(world, (double) event.x, (double) event.y, (double) event.z, new ItemStack(ExtraTools.glowDust)));
		}
		
		else if (event.block == ExtraTools.fermiteOre) {
			if (val <= chance) world.spawnEntityInWorld(new EntityItem(world, (double) event.x, (double) event.y, (double) event.z, new ItemStack(ExtraTools.fermiteDust)));
		}
		
		else if (event.block == ExtraTools.tanzaniteOre) {
			if (val <= chance) world.spawnEntityInWorld(new EntityItem(world, (double) event.x, (double) event.y, (double) event.z, new ItemStack(ExtraTools.tanzaniteDust)));
		}
		
		else if (event.block == ExtraTools.xyniteOre) {
			if (val <= chance) world.spawnEntityInWorld(new EntityItem(world, (double) event.x, (double) event.y, (double) event.z, new ItemStack(ExtraTools.xyniteDust)));
		}

		else return;
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {

		if (!(event.entityLiving instanceof EntityPlayer)) return;
		else {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player.capabilities.isCreativeMode) return;

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
			else bootCheck = legCheck = chestCheck = helmCheck = false;

			if (currentBoot == boot) {
				bootCheck = true;
				if (!player.isCollidedVertically) player.fallDistance = 0f;
			}

			if (currentLeg == leg) {
				legCheck = true;
				// player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, 0));
				if (!player.isSneaking()) {
					if (player.stepHeight < 1.0f) player.stepHeight = 1.0f;
					// if (player.capabilities.getWalkSpeed() < 0.15f) player.capabilities.setPlayerWalkSpeed(0.15f);
				}
				
				else {
					if (player.stepHeight > 0.5f) player.stepHeight = 0.5f;
					// if (player.capabilities.getWalkSpeed() > 0.1f) player.capabilities.setPlayerWalkSpeed(0.1f);
				}
			}

			if (currentChest == chest) {
				chestCheck = true;
				if (!player.isBurning()) player.removePotionEffect(Potion.fireResistance.id);
				else player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 5, 0));
			}

			if (currentHelm == helm) {
				helmCheck = true;
				if (player.isInWater()) {
					player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 5, 0));
					player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 0));
				}
				else {
					player.removePotionEffect(Potion.waterBreathing.id);
					player.removePotionEffect(Potion.nightVision.id);
				}
			}

			if (bootCheck && legCheck && chestCheck && helmCheck) {
				if (!player.capabilities.allowFlying) player.capabilities.allowFlying = true;
				setAllowedFly(true);
			}
			else {
				if (currentHelm == null || currentChest == null || currentLeg == null || currentBoot == null) {
					if (player.capabilities.allowFlying) player.capabilities.allowFlying = false;
					if (currentLeg != leg && player.stepHeight != 0.5f) player.stepHeight = 0.5f;
				}
				setAllowedFly(false);
				// if (player.capabilities.getWalkSpeed() != 0.1f) player.capabilities.setPlayerWalkSpeed(0.1f);
			}

			bootCheck = legCheck = chestCheck = helmCheck = false;
		}

	}
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event) {
		if (!(event.entity instanceof EntityPlayerMP)) return;
		else {
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			if (!ExtraTools.instance.proxy.updateFlag) {
				short build = -1;
				String url = "";
				
				// Grabbing first index of entry's keys and values and store this data.
				Iterator iter = ExtraTools.proxy.getEntry().iterator();
				while (iter.hasNext()) {
					Entry<Short, String> current = (Entry<Short, String>) iter.next();
					build = current.getKey();
					url = current.getValue();
					break;
				}
				
				// Output info to joining player.
				ChatHelper helper = new ChatHelper();
				player.addChatComponentMessage(helper.comp("[ExtraTools+] Found an update! Latest build: " + build));
				// player.addChatComponentMessage(helper.compURL("You can get this at:", url, true));
				player.addChatComponentMessage(helper.compURL("You can get this at:", "http://goo.gl/nYTUfU", true));
			}
		}
	}
	
	/*
	 * Event called when user hovers over my items at the end of the <List>.
	 */
	@SubscribeEvent
	public void onItemHover(ItemTooltipEvent event) {
		Item currentItem = event.itemStack.getItem();

		if (currentItem == Item.getItemFromBlock(ExtraTools.glowFurnaceOff)) event.toolTip.add("Smelts items at " + (int) glowCalcTime + "% faster rate!");
		else if (currentItem == Item.getItemFromBlock(ExtraTools.extraSmoothStone)) event.toolTip.add("Smooth, as without silk");
		else if (currentItem == Item.getItemFromBlock(ExtraTools.safeGlass)) event.toolTip.add("Stepping on broken glass, a thing of the past!");
		else if (currentItem == Item.getItemFromBlock(ExtraTools.glowPressurePlate)) {
			event.toolTip.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Provides a touch of Glow!");
			event.toolTip.add(EnumChatFormatting.WHITE + "Only Players may use it!");
		}
		else if (currentItem == Item.getItemFromBlock(ExtraTools.glowChest)) {
			if (event.itemStack.hasTagCompound()) {
				int maxSize = (7 * 9);
				int has = 0;
				int[] array = event.itemStack.stackTagCompound.getIntArray("Items");
				for (int i = 0; i < array.length; i++) {
					if (Item.getItemById(array[i]) != null) has++;  
				}

				event.toolTip.add(EnumChatFormatting.GREEN + "Contents: " + EnumChatFormatting.WHITE + has + " / " + maxSize);
			}
		}
		
		else if (currentItem == Item.getItemFromBlock(ExtraTools.tickTorch)) {
			event.toolTip.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Right click to expand area.");
			event.toolTip.add(EnumChatFormatting.WHITE + "  Shift + right click to shrink area.");
		}

		else return;
	}

	// Future possible commands
	/*
	 * @ForgeSubscribe public void onPlayerChat(ServerChatEvent event) { }
	 */

}
