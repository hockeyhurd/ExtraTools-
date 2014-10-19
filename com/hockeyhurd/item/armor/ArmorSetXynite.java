package com.hockeyhurd.item.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Xynite armour set class.
 * 
 * @author hockeyhurd
 * @version Oct 19, 2014
 */
public class ArmorSetXynite extends ItemArmor {

	public ArmorMaterial material;
	private final int armorType;
	public String nameToAdd;

	/**
	 * Default constructor.
	 * 
	 * @param xyniteArmorMat = armor material.
	 * @param par3
	 * @param durabillity =  durability.
	 * @param pathMat = path to material.
	 * @param type = type id.
	 */
	public ArmorSetXynite(ArmorMaterial xyniteArmorMat, int par3, int durabillity, String pathMat, int type) {
		super(xyniteArmorMat, par3, durabillity);
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.maxStackSize = 1;
		this.material = xyniteArmorMat;
		this.armorType = type;
		this.canRepair = true;
		this.setMaxDamage(xyniteArmorMat.getDurability(durabillity));
		xyniteArmorMat.getDamageReductionAmount(durabillity);

		nameToAdd = pathMat;
	}

	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity e, int slot, String type) {
		if (stack.toString().contains("leggings")) return (ExtraTools.assetsDir + nameToAdd + "_2.png");
		if (stack.toString().contains("Leggings") && stack.getItem() == ExtraTools.xyniteLegging) return (ExtraTools.assetsDir + nameToAdd + "_2.png");

		return (ExtraTools.assetsDir + nameToAdd + "_1.png");
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconReg) {
		if (armorType == 0) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "XyniteHelmet");
		if (armorType == 1) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "XyniteChestplate");
		if (armorType == 2) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "XyniteLegging");
		if (armorType == 3) itemIcon = iconReg.registerIcon(ExtraTools.assetsDir + "XyniteBoot");
	}

}
