package com.hockeyhurd.entity.tileentity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.hockeyhurd.block.machines.BlockGlowChest;
import com.hockeyhurd.gui.ContainerGlowChest;

public class TileEntityGlowChest extends TileEntity implements IInventory {

	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;

	private ItemStack[] chestItemStacks = new ItemStack[81];
	private String field_145958_o;

	public TileEntityGlowChest() {
	}

	public int getSizeInventory() {
		return this.chestItemStacks.length;
	}

	public ItemStack getStackInSlot(int i) {
		return this.chestItemStacks[i];
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.chestItemStacks[slot] != null) {
			ItemStack itemstack = this.chestItemStacks[slot];
			this.chestItemStacks[slot] = null;
			return itemstack;
		}

		else return null;
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.chestItemStacks[slot] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) stack.stackSize = this.getInventoryStackLimit();
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.field_145958_o : "container.chest";
	}

	public boolean hasCustomInventoryName() {
		return this.field_145958_o != null && this.field_145958_o.length() > 0;
	}

	public void func_145951_a(String text) {
		this.field_145958_o = text;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		this.chestItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.chestItemStacks.length) {
				this.chestItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		if (par1NBTTagCompound.hasKey("CustomName")) {
			this.field_145958_o = par1NBTTagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestItemStacks.length; ++i) {
			if (this.chestItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.chestItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (this.hasCustomInventoryName()) par1NBTTagCompound.setString("CustomName", this.field_145958_o);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void updateEntity() {
		super.updateEntity();
		this.ticksSinceSync++;
		float f;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
			this.numPlayersUsing = 0;
			f = 5.0F;
			List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
					AxisAlignedBB.getBoundingBox((double) ((float) this.xCoord - f), (double) ((float) this.yCoord - f), (double) ((float) this.zCoord - f), (double) ((float) (this.xCoord + 1) + f), (double) ((float) (this.yCoord + 1) + f), (double) ((float) (this.zCoord + 1) + f)));
			Iterator iterator = list.iterator();

			while (iterator.hasNext()) {
				EntityPlayer entityplayer = (EntityPlayer) iterator.next();

				if (entityplayer.openContainer instanceof ContainerGlowChest) {
					IInventory iinventory = ((ContainerGlowChest) entityplayer.openContainer).getLowerChestInventory();

					if (iinventory == this /* || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this) */) {
						this.numPlayersUsing++;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		f = 0.1F;
		double d2;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			double d1 = (double) this.xCoord + 0.5D;
			d2 = (double) this.zCoord + 0.5D;
			d2 += 0.5D;

			/*
			 * if (this.adjacentChestZPos != null) { d2 += 0.5D; }
			 * 
			 * if (this.adjacentChestXPos != null) { d1 += 0.5D; }
			 */

			this.worldObj.playSoundEffect(d1, (double) this.yCoord + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f1 = this.lidAngle;

			if (this.numPlayersUsing > 0) {
				this.lidAngle += f;
			}
			else {
				this.lidAngle -= f;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f2 = 0.5F;

			if (this.lidAngle < f2 && f1 >= f2) {
				d2 = (double) this.xCoord + 0.5D;
				double d0 = (double) this.zCoord + 0.5D;
				d2 += 0.5D;

				/*
				 * if (this.adjacentChestZPos != null) { d0 += 0.5D; }
				 * 
				 * if (this.adjacentChestXPos != null) { d2 += 0.5D; }
				 */

				this.worldObj.playSoundEffect(d2, (double) this.yCoord + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	public void openInventory() {
		if (this.numPlayersUsing < 0) {
			this.numPlayersUsing = 0;
		}

		this.numPlayersUsing++;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}

	public void closeInventory() {
		if (this.getBlockType() instanceof BlockGlowChest) {
			this.numPlayersUsing--;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
		}
	}

	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return true;
	}

	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return side != 0 || slot != 1 || stack.getItem() == Items.bucket;
	}

	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		return null;
	}

	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

}
