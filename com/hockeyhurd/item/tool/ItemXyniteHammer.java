package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.hockeyhurd.extratools.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.ChatHelper;
import com.hockeyhurd.util.TimerHelper;
import com.hockeyhurd.util.Waila;
import com.hockeyhurd.util.interfaces.IToolToggle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemXyniteHammer extends ItemPickaxe implements IToolToggle {

	private Material[] mineAble;
	private TimerHelper th;
	private ChatHelper ch;

	public int i = 0;
	public boolean f = false;

	public ItemXyniteHammer(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("XyniteHammer");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setHarvestLevel(this.toString(), 3);
		this.setMaxDamage(2000);

		mineAble = new Material[] {
				Material.rock, Material.iron
		};

		th = new TimerHelper(10, 2);
		ch = new ChatHelper();
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "XyniteHammer");
	}

	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
		if (this.i != i) this.i = i;
		if (this.f != f) this.f = f;
	}

	public void writeToNBT(ItemStack stack, boolean value) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) nbt = stack.stackTagCompound = new NBTTagCompound();
		nbt.setBoolean("Mode", value);
	}

	public boolean readValueFromNBT(ItemStack stack) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) nbt = stack.stackTagCompound = new NBTTagCompound();
		if (!nbt.hasKey("Mode")) writeToNBT(stack, true);
		return nbt.getBoolean("Mode");
	}

	public void doKeyBindingAction(EntityPlayer player, ItemStack stack, int key) {
		boolean tempVal = readValueFromNBT(stack) ? false : true;
		writeToNBT(stack, tempVal);
		player.addChatComponentMessage(ch.comp("Mode: " + (!tempVal ? "1x1 area." : "3x3 area."), EnumChatFormatting.GOLD));
	}

	// When player mines a block, mine a 3x3 area.
	public boolean onBlockDestroyed(ItemStack stack, World world, Block blockDestroyed, int x, int y, int z, EntityLivingBase entityLiving) {

		// If for some reason this instance of event is called and the entity is not a player, just return true and mine a single block.
		if (!(entityLiving instanceof EntityPlayer)) return true;

		EntityPlayer player = (EntityPlayer) entityLiving;
		BlockHelper bh = new BlockHelper(world, player);
		Block block = bh.getBlock(x, y, z);
		Material mat = bh.getBlockMaterial(block);
		boolean contains = false;

		for (int i = 0; i < mineAble.length; i++) {
			if (mineAble[i] == mat) {
				contains = true;
				break;
			}
		}

		Waila waila = new Waila(stack, world, player, block, false, false);
		// Sets offset or number of blocks in all directions that are possible to mine.
		waila.setOffset(1);

		// Makes sure the matwhitelist is in sync.
		waila.setMatWhiteList(mineAble);

		if (!world.isRemote && (!th.use || th.excuser())) {
			if (readValueFromNBT(stack) && contains) waila.finder();
			th.setUse(true);
		}

		return true;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Mines a 3x3 area");
		list.add(EnumChatFormatting.GREEN + "Mode: " + EnumChatFormatting.WHITE + (!readValueFromNBT(stack) ? "1x1 area." : "3x3 area."));
	}

}
