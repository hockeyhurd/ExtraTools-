package com.hockeyhurd.item.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemRendererGlowRock implements IItemRenderer {

	static final boolean testFlagColour = false;
	
	public ItemRendererGlowRock() {
	}

	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	final Tessellator tess = Tessellator.instance;
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		tess.startDrawingQuads();

		// adjust rendering space to match what caller expects
		boolean mustTranslate = false;
		switch (type) {
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON: {
				break; // caller expects us to render over [0,0,0] to [1,1,1], no translation necessary
			}
			case ENTITY:
			case INVENTORY: {
				// translate our coordinates so that [0,0,0] to [1,1,1] translates to the [-0.5, -0.5, -0.5] to [0.5, 0.5, 0.5] expected by the caller.
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				mustTranslate = true;   // must undo the translation when we're finished rendering
				break;
			}
			default:
				break; // never here
		}

		// xpos face blue
		IIcon icon = item.getItem().getIconFromDamage(5);
		// tessellator.setNormal(1.0F, 0.0F, 0.0F);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		if (testFlagColour) tess.setColorOpaque(0, 0, 255);
		tess.addVertexWithUV(1.0, 0.0, 0.0, (double) icon.getMaxU(), (double) icon.getMaxV());
		tess.addVertexWithUV(1.0, 1.0, 0.0, (double) icon.getMaxU(), (double) icon.getMinV());
		tess.addVertexWithUV(1.0, 1.0, 1.0, (double) icon.getMinU(), (double) icon.getMinV());
		tess.addVertexWithUV(1.0, 0.0, 1.0, (double) icon.getMinU(), (double) icon.getMaxV());

		// xneg face purple
		icon = item.getItem().getIconFromDamage(4);
		// tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		if (testFlagColour) tess.setColorOpaque(255, 0, 255);
		tess.addVertexWithUV(0.0, 0.0, 1.0, (double) icon.getMaxU(), (double) icon.getMaxV());
		tess.addVertexWithUV(0.0, 1.0, 1.0, (double) icon.getMaxU(), (double) icon.getMinV());
		tess.addVertexWithUV(0.0, 1.0, 0.0, (double) icon.getMinU(), (double) icon.getMinV());
		tess.addVertexWithUV(0.0, 0.0, 0.0, (double) icon.getMinU(), (double) icon.getMaxV());

		// zneg face white
		icon = item.getItem().getIconFromDamage(2);
		// tessellator.setNormal(0.0F, 0.0F, -1.0F);
		if (testFlagColour) tess.setColorOpaque(255, 255, 255);
		tess.addVertexWithUV(0.0, 0.0, 0.0, (double) icon.getMaxU(), (double) icon.getMaxV());
		tess.addVertexWithUV(0.0, 1.0, 0.0, (double) icon.getMaxU(), (double) icon.getMinV());
		tess.addVertexWithUV(1.0, 1.0, 0.0, (double) icon.getMinU(), (double) icon.getMinV());
		tess.addVertexWithUV(1.0, 0.0, 0.0, (double) icon.getMinU(), (double) icon.getMaxV());

		// zpos face green
		icon = item.getItem().getIconFromDamage(3);
		// tessellator.setNormal(0.0F, 0.0F, -1.0F);
		if (testFlagColour) tess.setColorOpaque(0, 255, 0);
		tess.addVertexWithUV(1.0, 0.0, 1.0, (double) icon.getMaxU(), (double) icon.getMaxV());
		tess.addVertexWithUV(1.0, 1.0, 1.0, (double) icon.getMaxU(), (double) icon.getMinV());
		tess.addVertexWithUV(0.0, 1.0, 1.0, (double) icon.getMinU(), (double) icon.getMinV());
		tess.addVertexWithUV(0.0, 0.0, 1.0, (double) icon.getMinU(), (double) icon.getMaxV());

		// ypos face red
		icon = item.getItem().getIconFromDamage(1);
		// tessellator.setNormal(0.0F, 1.0F, 0.0F);
		if (testFlagColour) tess.setColorOpaque(255, 0, 0);
		tess.addVertexWithUV(1.0, 1.0, 1.0, (double) icon.getMaxU(), (double) icon.getMaxV());
		tess.addVertexWithUV(1.0, 1.0, 0.0, (double) icon.getMaxU(), (double) icon.getMinV());
		tess.addVertexWithUV(0.0, 1.0, 0.0, (double) icon.getMinU(), (double) icon.getMinV());
		tess.addVertexWithUV(0.0, 1.0, 1.0, (double) icon.getMinU(), (double) icon.getMaxV());

		// yneg face yellow
		icon = item.getItem().getIconFromDamage(0);
		// tessellator.setNormal(0.0F, -1.0F, 0.0F);
		if (testFlagColour) tess.setColorOpaque(255, 255, 0);
		tess.addVertexWithUV(0.0, 0.0, 1.0, (double) icon.getMaxU(), (double) icon.getMaxV());
		tess.addVertexWithUV(0.0, 0.0, 0.0, (double) icon.getMaxU(), (double) icon.getMinV());
		tess.addVertexWithUV(1.0, 0.0, 0.0, (double) icon.getMinU(), (double) icon.getMinV());
		tess.addVertexWithUV(1.0, 0.0, 1.0, (double) icon.getMinU(), (double) icon.getMaxV());

		tess.draw();

		if (mustTranslate) GL11.glTranslatef(0.5F, 0.5F, 0.5F);

	}

}
