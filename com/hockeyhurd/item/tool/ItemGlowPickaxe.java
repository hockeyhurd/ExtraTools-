package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.hockeyhurd.extratools.ExtraTools;
import com.hockeyhurd.util.TimerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGlowPickaxe extends ItemPickaxe {

	private final Block TORCH = ExtraTools.glowTorch;
	private TimerHelper th;

	public ItemGlowPickaxe(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("GlowPickaxeUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		th = new TimerHelper(20, 2);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowPickaxeUnbreakable");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
		boolean used = false;
		if (!world.isRemote) {
			final ItemStack TORCH_STACK = new ItemStack(TORCH, 1);
			if (TORCH_STACK.getItem() instanceof ItemBlock) {
				if (!th.getUse() || th.excuser()) {
					used = TORCH_STACK.getItem().onItemUse(TORCH_STACK, player, world, x, y, z, side, clickX, clickY, clickZ);
					th.setUse(true);
				}
			}
		}
		
		if (!th.getUse()) player.swingItem();
		return used;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Unbreakable!");
		list.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Right click to place GlowTorch");
	}

}
