package com.hockeyhurd.block.renderer;

import static com.hockeyhurd.extratools.ClientProxy.glowRockRenderType;
import static com.hockeyhurd.extratools.ClientProxy.renderPass;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlowRockRenderer implements ISimpleBlockRenderingHandler {

	public GlowRockRenderer() {
	}

	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (renderPass == 0) drawRock(Blocks.glowstone, x, y, z, 0.9d);
		else renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	public void drawRock(Block block, float x, float y, float z, double sc) {
		// change the passed integer coordinates into double precision floats, and set the height on the y axis
		double xx = (double) x;
		double yy = (double) y + 0.1d;
		double zz = (double) z;

		// this is the scale of the squares, in blocks
		double scale = sc <= 0d ? 0.9d : sc;

		// get the tessellator instance
		Tessellator tess = Tessellator.instance;

		// set the texture
		IIcon icon = block.getBlockTextureFromSide(0);
		// IIcon var11 = ExtraTools.glowRock.getIcon(0, 0);

		// set the uv coordinates
		double minU = icon.getMinU();
		double maxU = icon.getMaxU(); // var11.getMinV();
		double minV = icon.getMinV(); // var11.getMaxU();
		double maxV = icon.getMaxV();

		// here the scale is changed
		double adjustScale = 0.45D * (double) scale;

		// offset the vertices from the centre of the block
		double xxMin = xx + 0.5D - adjustScale;
		double xxMax = xx + 0.5D + adjustScale;
		double zzMin = zz + 0.5D - adjustScale;
		double zzMax = zz + 0.5D + adjustScale;

		// not create the vertices
		/*
		 * tess.addVertexWithUV(uuMin, yy + (double) scale, vvMin, minU, minV); tess.addVertexWithUV(uuMin, yy + 0.0D, vvMin, minU, maxV); tess.addVertexWithUV(uuMax, yy + 0.0D, vvMax, maxY, maxV); tess.addVertexWithUV(uuMax, yy + (double) scale, vvMax, maxY, minV); tess.addVertexWithUV(uuMax, yy +
		 * (double) scale, vvMax, minU, minV); tess.addVertexWithUV(uuMax, yy + 0.0D, vvMax, minU, maxV); tess.addVertexWithUV(uuMin, yy + 0.0D, vvMin, maxY, maxV); tess.addVertexWithUV(uuMin, yy + (double) scale, vvMin, maxY, minV); tess.addVertexWithUV(uuMin, yy + (double) scale, vvMax, minU,
		 * minV); tess.addVertexWithUV(uuMin, yy + 0.0D, vvMax, minU, maxV); tess.addVertexWithUV(uuMax, yy + 0.0D, vvMin, maxY, maxV); tess.addVertexWithUV(uuMax, yy + (double) scale, vvMin, maxY, minV); tess.addVertexWithUV(uuMax, yy + (double) scale, vvMin, minU, minV);
		 * tess.addVertexWithUV(uuMax, yy + 0.0D, vvMin, minU, maxV); tess.addVertexWithUV(uuMin, yy + 0.0D, vvMax, maxY, maxV); tess.addVertexWithUV(uuMin, yy + (double) scale, vvMax, maxY, minV);
		 */

		// zz+
		tess.addVertexWithUV(xxMin, yy + scale, zzMax, minU, minV);
		tess.addVertexWithUV(xxMin, yy + 0.0D, zzMax, minU, maxV);
		tess.addVertexWithUV(xxMax, yy + 0.0D, zzMax, maxU, maxV);
		tess.addVertexWithUV(xxMax, yy + scale, zzMax, maxU, minV);

		// zz-
		tess.addVertexWithUV(xxMax, yy + scale, zzMin, minU, minV);
		tess.addVertexWithUV(xxMax, yy + 0.0D, zzMin, minU, maxV);
		tess.addVertexWithUV(xxMin, yy + 0.0D, zzMin, maxU, maxV);
		tess.addVertexWithUV(xxMin, yy + scale, zzMin, maxU, minV);

		// xx-
		tess.addVertexWithUV(xxMin, yy + scale, zzMin, minU, minV);
		tess.addVertexWithUV(xxMin, yy + 0.0D, zzMin, minU, maxV);
		tess.addVertexWithUV(xxMin, yy + 0.0D, zzMax, maxU, maxV);
		tess.addVertexWithUV(xxMin, yy + scale, zzMax, maxU, minV);

		// xx+
		tess.addVertexWithUV(xxMax, yy + scale, zzMax, minU, minV);
		tess.addVertexWithUV(xxMax, yy + 0.0D, zzMax, minU, maxV);
		tess.addVertexWithUV(xxMax, yy + 0.0D, zzMin, maxU, maxV);
		tess.addVertexWithUV(xxMax, yy + scale, zzMin, maxU, minV);

		// yy+
		tess.addVertexWithUV(xxMin, yy + scale - 0.01d, zzMin, minU, minV);
		tess.addVertexWithUV(xxMin, yy + scale - 0.01d, zzMax, minU, maxV);
		tess.addVertexWithUV(xxMax, yy + scale - 0.01d, zzMax, maxU, maxV);
		tess.addVertexWithUV(xxMax, yy + scale - 0.01d, zzMin, maxU, minV);

		// yy--
		tess.addVertexWithUV(xxMin, yy + 0.0d, zzMin, minU, minV);
		tess.addVertexWithUV(xxMax, yy + 0.0d, zzMin, minU, maxV);
		tess.addVertexWithUV(xxMax, yy + 0.0d, zzMax, maxU, maxV);
		tess.addVertexWithUV(xxMin, yy + 0.0d, zzMax, maxU, minV);
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	public int getRenderId() {
		return glowRockRenderType;
	}

}
