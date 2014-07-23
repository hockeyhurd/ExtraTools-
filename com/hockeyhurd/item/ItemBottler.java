package com.hockeyhurd.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;

public class ItemBottler extends Item {

	private EntityLivingBase entityToSpawn;

	public ItemBottler() {
		super();
		this.setUnlocalizedName("EntityBottler");
		this.setCreativeTab(ExtraTools.myCreativeTab);
		this.setMaxDamage(1);
		if (new ItemStack(this).getItemDamage() > 0) this.setMaxStackSize(1);
		else this.setMaxStackSize(64);
	}

	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(ExtraTools.modPrefix + "EntityBottler");
	}

	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() > 0 ? true : false;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (!(entity instanceof EntityMob || entity instanceof EntityAnimal)) return true;
		
		this.setUnlocalizedName("Bottled: " + entity.toString());
		this.entityToSpawn = (EntityLivingBase) entity;

		int emptySlot = -1;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				if (player.inventory.getStackInSlot(x + y * 9) == (ItemStack) null) {
					emptySlot = x * y;
					break;
				}
			}
		}

		int slotNum = 0;
		for (int i = 0; i < player.inventory.getHotbarSize(); i++) {
			if (player.inventory.getStackInSlot(i) == stack) slotNum = i;
			else if (emptySlot != -1 && player.inventory.getStackInSlot(i) == (ItemStack) null) emptySlot = i;
		}

		decreaseStackSize(stack, slotNum, player);

		if (emptySlot == -1) player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(this, 1, 1)));
		else if (emptySlot > 0) player.inventory.setInventorySlotContents(emptySlot, new ItemStack(this, 1, 1));

		player.onUpdate();
		entity.setDead();

		return true;
	}

	private void decreaseStackSize(ItemStack stack, int slotNum, EntityPlayer player) {
		if (stack.stackSize > 0) stack.stackSize--;
		else if (stack.stackSize < 1) {
			stack.stackSize = 0;
			player.inventory.setInventorySlotContents(slotNum, (ItemStack) null);
		}
	}

	private boolean canPlaceEntity(ItemStack stack) {
		return stack.getItemDamage() > 0;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!canPlaceEntity(stack)) return stack;

		else {
			if (this.entityToSpawn != null && !world.isRemote) {
				spawnCreature(world, player.posX, player.posY, player.posZ);
			}

			int slotNum = checkInvForStack(new ItemStack(this, 1, 0), player);
			if (slotNum != -1) {
				player.inventory.getStackInSlot(slotNum).stackSize++;
				player.onUpdate();
				return stack;
			}
			else if (slotNum == -1) checkInvForStack((ItemStack) null, player);

			if (slotNum != -1) {
				player.inventory.setInventorySlotContents(slotNum, new ItemStack(this, 1, 0));
				player.onUpdate();
				return stack;
			}
			else if (slotNum == -1) world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(this, 1, 1)));

			player.onUpdate();
			return stack;
		}
	}

	public Entity spawnCreature(World world, double x, double y, double z) {
		Entity entity = null;

		for (int j = 0; j < 1; ++j) {
			entity = this.entityToSpawn;

			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLiving entityliving = (EntityLiving) entity;
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
				entityliving.rotationYawHead = entityliving.rotationYaw;
				entityliving.renderYawOffset = entityliving.rotationYaw;
				entityliving.onSpawnWithEgg((IEntityLivingData) null);
				world.spawnEntityInWorld(entity);
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

}
