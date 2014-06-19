package com.hockeyhurd.item.armor;

import com.hockeyhurd.main.ExtraTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorSetGlow extends ItemArmor {

	public EnumArmorMaterial material;
	public String nameToAdd;
	
	public ArmorSetGlow(int id, EnumArmorMaterial glowArmorMat, int par3, int durabillity, String pathMat) {
		super(id, glowArmorMat, par3, durabillity);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.maxStackSize = 1;
		this.material = glowArmorMat;
		this.canRepair = true;
		this.setMaxDamage(glowArmorMat.getDurability(durabillity));
		glowArmorMat.getDamageReductionAmount(durabillity);
		
		nameToAdd = pathMat;
	}
	
	public String getArmorTexture(ItemStack stack, Entity e, int slot, int layer) {
		if (stack.toString().contains("leggings")) {
			return (ExtraTools.modPrefix + nameToAdd + "_2.png");
		}
		
		if (stack.toString().contains("Leggings") && itemID == ExtraTools.glowLegging.itemID) {
			return (ExtraTools.modPrefix + nameToAdd + "_2.png");
		}
		
		return (ExtraTools.modPrefix + nameToAdd + "_1.png");
	}
	
	public void registerIcons(IconRegister iconReg) {
		if (itemID == ExtraTools.glowHelmet.itemID) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowHelmet");
		if (itemID == ExtraTools.glowChestplate.itemID) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowChestplate");
		if (itemID == ExtraTools.glowLegging.itemID) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowLegging");
		if (itemID == ExtraTools.glowBoot.itemID) itemIcon = iconReg.registerIcon(ExtraTools.modPrefix + "GlowBoot");
	}
	
	/*public boolean hasEffect(ItemStack stack) {
		return false;
	}*/
	
}
