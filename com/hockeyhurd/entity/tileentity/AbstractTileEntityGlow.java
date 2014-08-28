package com.hockeyhurd.entity.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.hockeyhurd.block.machines.AbstractBlockMachine;
import com.hockeyhurd.mod.ExtraTools;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AbstractTileEntityGlow extends TileEntity implements ISidedInventory {

	protected static int[] slots_top;
	protected static int[] slots_bottom;
	protected static int[] slots_sides;
	
	/**
	 * Include only slots in the UI and specifically not the player's inventory.
	 */
	protected ItemStack[] invContents;
	protected String customName;
	public int burnTime;
	public int currentBurnTime;
	public int cookTime;
	public static int defaultCookTime = 200;
	public static int scaledTime = (defaultCookTime / 10) * 5;
	
	public AbstractTileEntityGlow() {
		initContentsArray();
		initSlotsArray();
	}
	
	protected abstract void initContentsArray();
	protected abstract void initSlotsArray();
	
	public int getSizeInventory() {
		return this.invContents.length;
	}
	
	public ItemStack getStackInSlot(int slot) {
		return this.invContents[slot];
	}
	
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.invContents[par1] != null) {
			ItemStack itemstack;

			if (this.invContents[par1].stackSize <= par2) {
				itemstack = this.invContents[par1];
				this.invContents[par1] = null;
				return itemstack;
			}
			else {
				itemstack = this.invContents[par1].splitStack(par2);

				if (this.invContents[par1].stackSize == 0) this.invContents[par1] = null;
				return itemstack;
			}
		}
		else return null;
	}
	
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.invContents[slot] != null) {
			ItemStack itemstack = this.invContents[slot];
			this.invContents[slot] = null;
			return itemstack;
		}
		else return null;
	}
	
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.invContents[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) stack.stackSize = this.getInventoryStackLimit();
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.generic";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String text) {
		this.customName = text;
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		/*NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
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
		this.currentBurnTime = getItemBurnTime(this.invContents[1]); */

		if (par1NBTTagCompound.hasKey("CustomName")) {
			this.customName = par1NBTTagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		/*par1NBTTagCompound.setShort("BurnTime", (short) this.burnTime);
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

		par1NBTTagCompound.setTag("Items", nbttaglist);*/

		if (this.hasCustomInventoryName()) par1NBTTagCompound.setString("CustomName", this.customName);
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int i) {
		return this.cookTime * i / scaledTime;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) this.currentBurnTime = defaultCookTime;
		return this.burnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.burnTime > 0;
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
				AbstractBlockMachine.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1) this.markDirty();
	}
	
	protected boolean canSmelt() {
		if (this.invContents[0] == null) return false;
		else {
			// Check if the item in the slot 1 can be smelted (has a set furnace recipe).
			ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.invContents[0]);
			if (stack == null) return false;
			if (this.invContents[2] == null) return true;
			if (!this.invContents[2].isItemEqual(stack)) return false;
			
			// Add the result of the furnace recipe to the current stack size (already smelted so far).
			int result = this.invContents[2].stackSize + stack.stackSize;
			
			// Make sure we aren't going over the set stack limit's size.
			return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
		}
	}
	
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.invContents[0]);

			if (this.invContents[2] == null) {
				this.invContents[2] = itemstack.copy();
			}
			else if (this.invContents[2].isItemEqual(itemstack)) {
				invContents[2].stackSize += itemstack.stackSize;
			}

			this.invContents[0].stackSize--;

			if (this.invContents[0].stackSize <= 0) {
				this.invContents[0] = null;
			}
		}
	}
	
	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null) return 0;
		else {
			Item item = stack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != null && Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab) return 150;
				if (block.getMaterial() == Material.wood) return 300; 
				if (block == Blocks.coal_block) return 16000;
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item == Items.stick) return 100;
			if (item == Items.coal) return 1600;
			if (item == Items.lava_bucket) return 20000;
			if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
			if (item == Items.blaze_rod) return 2400;
			if (item == ExtraTools.glowCoal) return 1600 / 8 * 12;
			return GameRegistry.getFuelValue(stack);
		}
	}
	
	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	public void openInventory() {
	}

	public void closeInventory() {
	}
	
	public boolean isItemValidForSlot(int par1, ItemStack stack) {
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(stack) : true);
	}

	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
	}

	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return side != 0 || slot != 1 || stack.getItem() == Items.bucket;
	}
	
}
