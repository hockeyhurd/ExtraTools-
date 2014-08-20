package com.hockeyhurd.item.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.hockeyhurd.mod.ExtraTools;

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

	public String getArmorTexture(ItemStack stack, Entity e, int slot, String type) {
		if (stack.toString().contains("leggings")) { return (ExtraTools.modPrefix + nameToAdd + "_2.png"); }

		if (stack.toString().contains("Leggings") && stack.getItem() == ExtraTools.glowLegging) { return (ExtraTools.modPrefix + nameToAdd + "_2.png"); }

		return (ExtraTools.modPrefix + nameToAdd + "_1.png");
	}

	public void registerIcons(IIconRegister iconReg) {
		if (armorType == 0) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowHelmet");
		if (armorType == 1) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowChestplate");
		if (armorType == 2) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowLegging");
		if (armorType == 3) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowBoot");
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (armorType == 0) list.add(EnumChatFormatting.DARK_RED + "Ability: Underwater vision and breathing!");
		else if (armorType == 1) list.add(EnumChatFormatting.DARK_RED + "Ability: Protection from fire!");
		else if (armorType == 2) list.add(EnumChatFormatting.DARK_RED + "Ability: Step assist!");
		else if (armorType == 3) list.add(EnumChatFormatting.DARK_RED + "Ability: Protection from fall damage!");
		list.add(EnumChatFormatting.GREEN + "Ability: Flight (when combined)!");
	}

}
