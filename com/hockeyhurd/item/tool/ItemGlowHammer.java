package com.hockeyhurd.item.tool;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;

public class ItemGlowHammer extends ItemPickaxe {

	private final Block torch = ExtraTools.glowTorch;
	private List<Material> mineAble;
	private TimerHelper th;

	public ItemGlowHammer(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("GlowHammerUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		mineAble = new ArrayList<Material>();
		loadMats();

		th = new TimerHelper(10, 2);
	}

	private void loadMats() {
		mineAble.add(Material.rock);
		mineAble.add(Material.iron);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "GlowHammer");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
	}

	// When player mines a block, mine a 3x3 area.
	public boolean onBlockDestroyed(ItemStack stack, World world, Block blockDestroyed, int x, int y, int z, EntityLivingBase entityLiving) {

		// If for some reason this instance of event is called and the entity is not a player, just return true and mine a single block.
		if (!(entityLiving instanceof EntityPlayer)) return true;

		EntityPlayer player = (EntityPlayer) entityLiving;
		BlockHelper bh = new BlockHelper(world, player);
		Block block = bh.getBlock(x, y, z);
		Material mat = bh.getBlockMaterial(x, y, z);

		// If the player is sneaking void 3x3 mining,
		if (player.isSneaking() || !mineAble.contains(mat)) return true;

		Waila waila = new Waila(stack, world, player, block, false, false);

		// Sets offset or number of blocks in all directions that are possible to mine.
		waila.setOffset(1);

		// Makes sure the matwhitelist is in sync.
		waila.setMatWhiteList(mineAble);

		 if (!world.isRemote && (!th.use || th.excuser())) {
			waila.finder();
			th.setUse(true);
		 }

		return true;
	}

	// When player right click's, places a GlowTorch on given location.
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if (!world.isRemote) {
			Waila waila = new Waila(itemStack, world, entityPlayer, torch, true, false);
			waila.setOffset(1);
			if (!th.getUse() || th.excuser()) waila.finder();
			th.setUse(true);
		}
		return itemStack;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Unbreakable");
		list.add("Mines a 3x3 area");
		list.add("Right click to place GlowTorch");
	}

}
