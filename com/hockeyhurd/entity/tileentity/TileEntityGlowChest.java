package com.hockeyhurd.entity.tileentity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.hockeyhurd.block.machines.BlockGlowChest;
import com.hockeyhurd.gui.ContainerGlowChest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGlowChest extends TileEntity implements IInventory {

	// Math: 7 rows * 9 col + 3 rows * 9 col + hotbar (1 row * 9 col).
	private ItemStack[] chestContents = new ItemStack[(7 * 9) + (9 * 4)];

	/** Determines if the check for adjacent chests has taken place. */
	// public boolean adjacentChestChecked;
	/** Contains the chest tile located adjacent to this one (if any) */
	// public TileEntityGlowChest adjacentChestZNeg;
	/** Contains the chest tile located adjacent to this one (if any) */
	// public TileEntityGlowChest adjacentChestXPos;
	/** Contains the chest tile located adjacent to this one (if any) */
	// public TileEntityGlowChest adjacentChestXNeg;
	/** Contains the chest tile located adjacent to this one (if any) */
	// public TileEntityGlowChest adjacentChestZPos;
	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;
	/** The angle of the lid last tick */
	public float prevLidAngle;
	/** The number of players currently using this chest */
	public int numPlayersUsing;
	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;
	private int cachedChestType;
	private String customName;
	private static final String __OBFID = "CL_00000346";

	public TileEntityGlowChest() {
		this.cachedChestType = -1;
	}

	@SideOnly(Side.CLIENT)
	public TileEntityGlowChest(int type) {
		this.cachedChestType = type;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return chestContents.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int slot) {
		return this.chestContents[slot];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int slot, int numOfItems) {
		if (this.chestContents[slot] != null) {
			ItemStack itemstack;

			if (this.chestContents[slot].stackSize <= numOfItems) {
				itemstack = this.chestContents[slot];
				this.chestContents[slot] = null;
				this.markDirty();
				return itemstack;
			}
			else {
				itemstack = this.chestContents[slot].splitStack(numOfItems);

				if (this.chestContents[slot].stackSize == 0) {
					this.chestContents[slot] = null;
				}

				this.markDirty();
				return itemstack;
			}
		}
		else {
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI.
	 */
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.chestContents[slot] != null) {
			ItemStack itemstack = this.chestContents[slot];
			this.chestContents[slot] = null;
			return itemstack;
		}
		else return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.chestContents[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) stack.stackSize = this.getInventoryStackLimit();
		this.markDirty();
	}

	/**
	 * Returns the name of the inventory
	 */
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.glowchest";
	}

	/**
	 * Returns if the inventory is named
	 */
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public void readFromNBT(NBTTagCompound nbttagComp) {
		super.readFromNBT(nbttagComp);
		NBTTagList nbttaglist = nbttagComp.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];

		if (nbttagComp.hasKey("CustomName", 8)) this.customName = nbttagComp.getString("CustomName");

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length) this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	public void writeToNBT(NBTTagCompound nbttagComp) {
		super.writeToNBT(nbttagComp);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i) {
			if (this.chestContents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagComp.setTag("Items", nbttaglist);

		if (this.hasCustomInventoryName()) nbttagComp.setString("CustomName", this.customName);
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	/**
	 * Causes the TileEntity to reset all it's cached values for it's container Block, metadata and in the case of chests, the adjacent chest check
	 */
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
		// this.adjacentChestChecked = false;
	}

	private void func_145978_a(TileEntityGlowChest teChest, int id) {
		if (teChest.isInvalid()) {
			// this.adjacentChestChecked = false;
		}
	}

	public void updateEntity() {
		super.updateEntity();
		// this.checkForAdjacentChests();
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

					if (iinventory == this /* || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest) iinventory).isPartOfLargeChest(this) */) this.numPlayersUsing++;
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		f = 0.1F;
		double d2;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F /* && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null */) {
			double d1 = (double) this.xCoord + 0.5D;
			d2 = (double) this.zCoord + 0.5D;

			// if (this.adjacentChestZPos != null) d2 += 0.5D;
			// if (this.adjacentChestXPos != null) d1 += 0.5D;

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

			if (this.lidAngle < f2 && f1 >= f2 /* && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null */) {
				d2 = (double) this.xCoord + 0.5D;
				double d0 = (double) this.zCoord + 0.5D;

				// if (this.adjacentChestZPos != null) d0 += 0.5D;
				// if (this.adjacentChestXPos != null) d2 += 0.5D;

				this.worldObj.playSoundEffect(d2, (double) this.yCoord + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) this.lidAngle = 0.0F;
		}
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	public boolean receiveClientEvent(int eventID, int arg) {
		if (eventID == 1) {
			this.numPlayersUsing = arg;
			return true;
		}
		else return super.receiveClientEvent(eventID, arg);
	}

	public void openInventory() {
		if (this.numPlayersUsing < 0) this.numPlayersUsing = 0;

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

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	/**
	 * invalidates a tile entity
	 */
	public void invalidate() {
		super.invalidate();
		this.updateContainingBlockInfo();
		// this.checkForAdjacentChests();
	}

	public int func_145980_j() {
		if (this.cachedChestType == -1) {
			if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) return 0; 

			this.cachedChestType = 0;
		}

		return this.cachedChestType;
	}

}
