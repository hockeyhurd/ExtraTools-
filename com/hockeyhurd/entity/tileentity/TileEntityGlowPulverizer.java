package com.hockeyhurd.entity.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.hockeyhurd.block.machines.BlockGlowPulverizer;
import com.hockeyhurd.util.PulverizeRecipes;

public class TileEntityGlowPulverizer extends AbstractTileEntityGlow {

	public TileEntityGlowPulverizer() {
	}

	protected void initContentsArray() {
		this.invContents = new ItemStack[3];
	}

	protected void initSlotsArray() {
		this.slots_bottom = new int[] {
				2, 1
		};

		this.slots_top = new int[] {
			0
		};

		this.slots_sides = new int[] {
			1
		};
	}

	protected boolean canSmelt() {
		if (this.invContents[0] == null) return false;
		else {
			// Check if the item in the slot 1 can be smelted (has a set furnace recipe).
			ItemStack stack = PulverizeRecipes.pulverizeList(this.invContents[0]);
			if (stack == null) return false;
			if (this.invContents[2] == null) return true;
			if (!this.invContents[2].isItemEqual(stack)) return false;
			
			// Add the result of the furnace recipe to the current stack size (already smelted so far).
			int result = this.invContents[2].stackSize + stack.stackSize;
			
			// Make sure we aren't going over the set stack limit's size.
			return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
		}
	}
	
	public void updateEntity() {
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;

		if (this.burnTime > 0) this.burnTime--;

		if (!this.worldObj.isRemote) {
			if (this.burnTime == 0 && this.canSmelt()) {
				this.currentBurnTime = this.burnTime = getItemBurnTime(this.invContents[1]);

				if (this.burnTime > 0) {
					flag1 = true;

					if (this.invContents[1] != null) {
						this.invContents[1].stackSize--;

						if (this.invContents[1].stackSize == 0) {
							this.invContents[1] = this.invContents[1].getItem().getContainerItem(invContents[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt()) {
				this.cookTime++;

				if (this.cookTime == scaledTime) {
					this.cookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else this.cookTime = 0;

			if (flag != this.burnTime > 0) {
				flag1 = true;
				BlockGlowPulverizer.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1) this.markDirty();
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		this.invContents = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.invContents.length) {
				this.invContents[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.burnTime = par1NBTTagCompound.getShort("BurnTime");
		this.cookTime = par1NBTTagCompound.getShort("CookTime");
		this.currentBurnTime = getItemBurnTime(this.invContents[1]);

		if (par1NBTTagCompound.hasKey("CustomName")) {
			this.customName = par1NBTTagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short) this.burnTime);
		par1NBTTagCompound.setShort("CookTime", (short) this.cookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.invContents.length; ++i) {
			if (this.invContents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.invContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (this.hasCustomInventoryName()) par1NBTTagCompound.setString("CustomName", this.customName);
	}
	
}
