package com.hockeyhurd.block;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.hockeyhurd.extratools.ExtraTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowPressurePlate extends BlockBasePressurePlate {

	private Sensitivity sensitivityLevel;
	private static final String __OBFID = "CL_00000289";

	public BlockGlowPressurePlate(String text, Material material, Sensitivity sensitivity) {
		super(text, material);
		this.setBlockName("GlowPressurePlate");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHardness(1.0f);
		this.setLightLevel(0.5f);
		this.sensitivityLevel = sensitivity;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowPressurePlate");
	}

	protected int func_150065_e(World world, int x, int y, int z) {
		List list = null;

		if (this.sensitivityLevel == Sensitivity.everything) list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, this.func_150061_a(x, y, z));
		if (this.sensitivityLevel == Sensitivity.mobs) list = world.getEntitiesWithinAABB(EntityLivingBase.class, this.func_150061_a(x, y, z));
		if (this.sensitivityLevel == Sensitivity.players) list = world.getEntitiesWithinAABB(EntityPlayer.class, this.func_150061_a(x, y, z));

		if (list != null && !list.isEmpty()) {
			Iterator iterator = list.iterator();

			while (iterator.hasNext()) {
				Entity entity = (Entity) iterator.next();

				if (!entity.doesEntityNotTriggerPressurePlate()) return 15;
			}
		}

		return 0;
	}

	protected int func_150060_c(int id) {
		return id == 1 ? 15 : 0;
	}

	protected int func_150066_d(int state) {
		return state > 0 ? 1 : 0;
	}

	public static enum Sensitivity {
		everything, mobs, players;
		private static final String __OBFID = "CL_00000290";
	}

}
