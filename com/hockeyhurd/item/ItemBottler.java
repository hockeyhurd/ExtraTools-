package com.hockeyhurd.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.BlockHelper;
import com.hockeyhurd.util.Vector3IHelper;
import com.hockeyhurd.util.Waila;

public class ItemBottler extends Item {

	private String entityName;
	private int slotID = -1;
	private boolean updateInfo = false;
	private BlockHelper bh;

	public ItemBottler() {
		super();
		this.setUnlocalizedName("EntityBottler");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setMaxDamage(1);
		if (new ItemStack(this).getItemDamage() > 0) this.setMaxStackSize(1);
		else this.setMaxStackSize(64);

		this.bh = new BlockHelper();
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "EntityBottler");
	}

	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() > 0 ? true : false;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (!(entity instanceof EntityCreature) || stack.getItemDamage() > 0 || entity.worldObj.isRemote) return true;

		Item item = stack.getItem();
		ItemStack thisStack = new ItemStack(item, 1, 1);

		this.entityName = EntityList.getEntityString(entity);

		// Search inventory hotbar for current used stack's index.
		int slotNum = 0;
		for (int i = 0; i < player.inventory.getHotbarSize(); i++) {
			if (player.inventory.getStackInSlot(i) == stack) slotNum = i;
		}

		// Decrease the found stack index and size by -1.
		decreaseStackSize(stack, slotNum, player);

		int emptySlot = slotID = player.inventory.getFirstEmptyStack();
		if (emptySlot == -1) player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, thisStack));
		else if (emptySlot > 0) player.inventory.setInventorySlotContents(emptySlot, thisStack);

		updateInfo = true;
		player.onUpdate();
		entity.setDead();

		return true;
	}

	private void decreaseStackSize(ItemStack stack, int slotNum, EntityPlayer player) {
		if (stack.stackSize > 0) stack.stackSize--;
		if (stack.stackSize < 1) {
			stack.stackSize = 0;
			player.inventory.setInventorySlotContents(slotNum, (ItemStack) null);
		}
	}

	private boolean canPlaceEntity(ItemStack stack) {
		return stack.getItemDamage() > 0;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote || !canPlaceEntity(stack)) return stack;
		else {
			bh.setWorldPlayer(world, player);
			Waila waila = new Waila(stack, world, player, null, false, false);
			waila.finder(false);

			Vector3IHelper vec = waila.getVector3I();

			if (vec != null) {
				if (!bh.canMineBlock(0, 0, 0)) return stack;
				if (!player.canPlayerEdit(vec.getX(), vec.getY(), vec.getZ(), vec.getSideHit(), stack)) return stack;

				if (bh.getBlock(vec.getX(), vec.getY(), vec.getZ()) instanceof BlockLiquid) {
					Entity e = spawnCreature(stack, world, (double) vec.getX(), (double) vec.getY(), (double) vec.getZ());

					if (e != null) {
						if (e instanceof EntityLivingBase && stack.hasDisplayName()) ((EntityLiving) e).setCustomNameTag(stack.getDisplayName());
						if (!player.capabilities.isCreativeMode) stack.stackSize--;
					}
				}
			}

			return stack;
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (world.isRemote) return true;
		else {
			Block block = world.getBlock(x, y, z);
			x += Facing.offsetsXForSide[p_77648_7_];
			y += Facing.offsetsYForSide[p_77648_7_];
			z += Facing.offsetsZForSide[p_77648_7_];
			double d0 = 0.0D;
			if (p_77648_7_ == 1 && block.getRenderType() == 11) d0 = 0.5D;

			Entity entity = spawnCreature(stack, world, (double) x + 0.5D, (double) y + d0, (double) z + 0.5D);
			if (entity != null) {
				if (entity instanceof EntityLivingBase && stack.hasDisplayName()) ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
				if (!player.capabilities.isCreativeMode) stack.stackSize--;
			}

			return true;
		}
	}

	public Entity spawnCreature(ItemStack stack, World world, double x, double y, double z) {
		Entity entity = null;
		if (stack.getItemDamage() < 1) return entity;

		NBTTagCompound tag = null;
		if (stack != null && stack.stackTagCompound != null) tag = stack.stackTagCompound;

		for (int j = 0; j < 1; j++) {
			String temp = tag.getString("Entity");
			entity = EntityList.createEntityByName(temp, world);

			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLiving entityliving = (EntityLiving) entity;
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
				entityliving.rotationYawHead = entityliving.rotationYaw;
				entityliving.renderYawOffset = entityliving.rotationYaw;
				entityliving.onSpawnWithEgg((IEntityLivingData) null);

				world.spawnEntityInWorld(entity);
				// world.joinEntityInSurroundings(entity);
				entityliving.playLivingSound();
			}
		}

		return entity;
	}

	private int checkInvForStack(ItemStack stack, EntityPlayer player) {
		int slotNum = -1;

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				if (player.inventory.getStackInSlot(x + y) == stack) slotNum = x + y;
			}
		}

		return slotNum;
	}
	
	public void onUpdate(ItemStack stack, World world, Entity e, int i, boolean f) {
		if (!(e instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) e;
		if (this.slotID == -1 || player.inventory.getStackInSlot(this.slotID) == null || player.inventory.getStackInSlot(this.slotID).getItemDamage() < 1 || !this.updateInfo) return;
		else {
			ItemStack theStack = player.inventory.getStackInSlot(this.slotID);
			theStack.stackTagCompound = new NBTTagCompound();
			theStack.stackTagCompound.setString("Entity", this.entityName);
			
			this.updateInfo = false;
		}
	}

	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.stackTagCompound = new NBTTagCompound();
		String savedText = stack.stackTagCompound.getString("Entity");
		if (ExtraTools.lh.nullCheck(savedText)) this.entityName = savedText;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (!hasEffect(stack) || !ExtraTools.lh.nullCheck(this.entityName)) return;
		NBTTagCompound tag = null;
		if (stack.stackTagCompound != null) tag = stack.stackTagCompound;
		
		String name = tag.getString("Entity");
		if (this.hasEffect(stack)) list.add(EnumChatFormatting.GREEN + "Entity: " + name);
	}

}