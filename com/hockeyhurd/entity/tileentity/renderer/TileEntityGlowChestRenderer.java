package com.hockeyhurd.entity.tileentity.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hockeyhurd.block.machines.BlockGlowChest;
import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityGlowChestRenderer extends TileEntityChestRenderer {

	private static final ResourceLocation texture_ = new ResourceLocation("extratools", "textures/blocks/GlowChest.png");
	// private static final ResourceLocation texture_ = new ResourceLocation("textures/entity/chest/normal.png");

	private ModelChest field_147510_h = new ModelChest();
	private static final String __OBFID = "CL_00000965";

	public TileEntityGlowChestRenderer() {
	}

	public void renderTileEntityAt(TileEntityGlowChest entity, double x, double y, double z, float p_147500_8_) {
		int i;

		if (!entity.hasWorldObj()) i = 0;
		else {
			Block block = entity.getBlockType();
			i = entity.getBlockMetadata();

			if (block instanceof BlockGlowChest && i == 0) {
				try {
					((BlockGlowChest) block).func_149954_e(entity.getWorldObj(), entity.xCoord, entity.yCoord, entity.zCoord);
				}
				catch (ClassCastException e) {
					FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", entity.xCoord, entity.yCoord, entity.zCoord);
				}
				i = entity.getBlockMetadata();
			}

		}
		
		ModelChest modelchest;
		modelchest = this.field_147510_h;

		if (entity.func_145980_j() == 0) this.bindTexture(texture_);

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short short1 = 0;

		if (i == 2) short1 = 180;
		if (i == 3) short1 = 0;
		if (i == 4) short1 = 90;
		if (i == 5) short1 = -90;

		// if (i == 2) GL11.glTranslatef(1.0F, 0.0F, 0.0F);
		// if (i == 5) GL11.glTranslatef(0.0F, 0.0F, -1.0F);

		GL11.glRotatef((float) short1, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float f1 = entity.prevLidAngle + (entity.lidAngle - entity.prevLidAngle) * p_147500_8_;
		float f2;

		f1 = 1.0F - f1;
		f1 = 1.0F - f1 * f1 * f1;
		modelchest.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
		modelchest.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		this.renderTileEntityAt((TileEntityGlowChest) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}

}
