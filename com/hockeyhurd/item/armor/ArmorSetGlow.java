package com.hockeyhurd.item.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

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

}
