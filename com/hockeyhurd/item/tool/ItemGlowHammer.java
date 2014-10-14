package com.hockeyhurd.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
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

public class ItemGlowHammer extends ItemPickaxe implements IToolToggle {

	private final Block TORCH = ExtraTools.glowTorch;
	private Material[] mineAble;
	private TimerHelper th;
	private ChatHelper ch;
	
	public int i = 0;
	public boolean f = false;

	public ItemGlowHammer(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("GlowHammerUnbreakable");
		this.setCreativeTab(ExtraTools.myCreativeTab);

		mineAble = new Material[] {
				Material.rock, Material.iron
		};

		th = new TimerHelper(10, 2);
		ch = new ChatHelper();
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.assetsDir + "GlowHammer");
	}

	// Makes sure the player can't press it more than once per second.
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		th.update();
		if (this.i != i) this.i = i;
		if (this.f != f) this.f = f;
		// if (!th.use && KeyEventHandler.keyValues = KeyEventHandler.)
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

	// When player right click's, places a GlowTorch on given location.
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
		list.add("Unbreakable");
		list.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Mines a 3x3 area");
		list.add(EnumChatFormatting.GREEN + "Ability: " + EnumChatFormatting.WHITE + "Right click to place GlowTorch");
		
		list.add(EnumChatFormatting.GREEN + "Mode: " + EnumChatFormatting.WHITE + (!readValueFromNBT(stack) ? "1x1 area." : "3x3 area."));
	}

}
