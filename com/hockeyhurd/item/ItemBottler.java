package com.hockeyhurd.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.hockeyhurd.mod.ExtraTools;

public class ItemBottler extends Item {

	private Entity entityToSpawn;
	private String entityName;

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
		if (!(entity instanceof EntityCreature)) return true;

		Item item = stack.getItem();
		ItemStack thisStack = new ItemStack(item, 1, 1);
		thisStack.setStackDisplayName("Bottled: " + entity.toString());

		this.entityToSpawn = entity;
		this.entityName = EntityList.getEntityString(entity);

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

		if (emptySlot == -1) player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, thisStack));
		else if (emptySlot > 0) player.inventory.setInventorySlotContents(emptySlot, thisStack);

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

	/*
	 * public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) { if (!canPlaceEntity(stack)) return stack;
	 * 
	 * else { if (this.entityToSpawn != null && !world.isRemote) { spawnCreature(world, player.posX, player.posY, player.posZ); }
	 * 
	 * int slotNum = checkInvForStack(new ItemStack(this, 1, 0), player); if (slotNum != -1) { player.inventory.getStackInSlot(slotNum).stackSize++; player.onUpdate(); return stack; } else if (slotNum == -1) checkInvForStack((ItemStack) null, player);
	 * 
	 * if (slotNum != -1) { player.inventory.setInventorySlotContents(slotNum, new ItemStack(this, 1, 0)); player.onUpdate(); return stack; } else if (slotNum == -1) world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(this, 1, 1)));
	 * 
	 * player.onUpdate(); return stack; } }
	 */

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote || !canPlaceEntity(stack)) return stack;
		else {
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

			if (movingobjectposition == null) return stack;
			else {
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					int xx = movingobjectposition.blockX;
					int yy = movingobjectposition.blockY;
					int zz = movingobjectposition.blockZ;

					if (!world.canMineBlock(player, xx, yy, zz)) return stack;
					if (!player.canPlayerEdit(xx, yy, zz, movingobjectposition.sideHit, stack)) return stack;

					if (world.getBlock(xx, yy, zz) instanceof BlockLiquid) {
						Entity entity = spawnCreature(world, (double) xx, (double) yy, (double) zz);

						if (entity != null) {
							if (entity instanceof EntityLivingBase && stack.hasDisplayName()) ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

							if (!player.capabilities.isCreativeMode) stack.stackSize--;
						}
					}
				}

				return stack;
			}
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (p_77648_3_.isRemote) {
			return true;
		}
		else {
			Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
			p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
			p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
			p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
			double d0 = 0.0D;

			if (p_77648_7_ == 1 && block.getRenderType() == 11) {
				d0 = 0.5D;
			}

			Entity entity = spawnCreature(p_77648_3_, (double) p_77648_4_ + 0.5D, (double) p_77648_5_ + d0, (double) p_77648_6_ + 0.5D);

			if (entity != null) {
				if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
					((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
				}

				if (!player.capabilities.isCreativeMode) {
					--stack.stackSize;
				}
			}

			return true;
		}
	}

	public Entity spawnCreature(World world, double x, double y, double z) {
		Entity entity = null;

		for (int j = 0; j < 1; j++) {
			// entity = this.entityToSpawn;
			entity = EntityList.createEntityByName(this.entityName, world);

			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLiving entityliving = (EntityLiving) entity;
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
				entityliving.rotationYawHead = entityliving.rotationYaw;
				entityliving.renderYawOffset = entityliving.rotationYaw;
				// entityliving.onSpawnWithEgg((IEntityLivingData) null);

				System.out.println("Enitity: " + entity + ", Stored Entity: " + this.entityToSpawn);
				world.spawnEntityInWorld(entity);
				world.joinEntityInSurroundings(entity);
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
