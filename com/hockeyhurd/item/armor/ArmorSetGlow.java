package com.hockeyhurd.item.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmorSetGlow extends ItemArmor {

	public ArmorMaterial material;
	private final int armorType;
	public String nameToAdd;

	public ArmorSetGlow(ArmorMaterial glowArmorMat, int par3, int durabillity, String pathMat, int type) {
		super(glowArmorMat, par3, durabillity);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.maxStackSize = 1;
		this.material = glowArmorMat;
		this.armorType = type;
		this.canRepair = true;
		this.setMaxDamage(glowArmorMat.getDurability(durabillity));
		glowArmorMat.getDamageReductionAmount(durabillity);

		nameToAdd = pathMat;
	}

	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity e, int slot, String type) {
		if (stack.toString().contains("leggings")) { return (ExtraTools.assetsDir + nameToAdd + "_2.png"); }

		if (stack.toString().contains("Leggings") && stack.getItem() == ExtraTools.glowLegging) { return (ExtraTools.assetsDir + nameToAdd + "_2.png"); }

		return (ExtraTools.assetsDir + nameToAdd + "_1.png");
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconReg) {
		if (armorType == 0) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "GlowHelmet");
		if (armorType == 1) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "GlowChestplate");
		if (armorType == 2) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "GlowLegging");
		if (armorType == 3) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "GlowBoot");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);

		if (world.getTotalWorldTime() % 20L == 0 && !player.capabilities.isCreativeMode) {
			
			boolean flag = true;
			byte counter = 0;
			
			if (player.getCurrentArmor(0) == null || player.getCurrentArmor(0).getItem() != ExtraTools.glowBoot) {
				flag = false;
				counter++;
			}
			
			if (player.getCurrentArmor(1) == null || player.getCurrentArmor(1).getItem() != ExtraTools.glowLegging) {
				flag = false;
				counter++;
			}
			
			if (player.getCurrentArmor(2) == null || player.getCurrentArmor(2).getItem() != ExtraTools.glowChestplate) {
				flag = false;
				counter++;
			}
			
			if (player.getCurrentArmor(3) == null || player.getCurrentArmor(3).getItem() != ExtraTools.glowHelmet) {
				flag = false;
				counter++;
			}
			
			if (counter == 4) return;
			
			if (flag) {
				// System.out.println("true");
				player.capabilities.allowFlying = true;
			}
			
			else {
				// System.out.println("false");
				player.capabilities.allowFlying = false;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (armorType == 0) list.add(EnumChatFormatting.DARK_RED + "Ability: Underwater vision and breathing!");
		else if (armorType == 1) list.add(EnumChatFormatting.DARK_RED + "Ability: Protection from fire!");
		else if (armorType == 2) list.add(EnumChatFormatting.DARK_RED + "Ability: Step assist!");
		else if (armorType == 3) list.add(EnumChatFormatting.DARK_RED + "Ability: Protection from fall damage!");
		list.add(EnumChatFormatting.GREEN + "Ability: Flight (when combined)!");
	}

}
