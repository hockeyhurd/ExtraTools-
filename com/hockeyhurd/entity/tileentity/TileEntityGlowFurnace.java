package com.hockeyhurd.entity.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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

import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.main.ExtraTools;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGlowFurnace extends TileEntity implements ISidedInventory {

	private static final int[] slots_top = new int[] {
		0
	};
	private static final int[] slots_bottom = new int[] {
			2, 1
	};
	private static final int[] slots_sides = new int[] {
		1
	};

	private ItemStack[] furnaceItemStacks = new ItemStack[3];
	public int burnTime;
	public int currentBurnTime;
	public int cookTime;
	private String field_94130_e;

	public TileEntityGlowFurnace() {
	}

	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	public ItemStack getStackInSlot(int i) {
		return this.furnaceItemStacks[i];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.furnaceItemStacks[par1] != null) {
			ItemStack itemstack;

			if (this.furnaceItemStacks[par1].stackSize <= par2) {
				itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			}
			else {
				itemstack = this.furnaceItemStacks[par1].splitStack(par2);

				if (this.furnaceItemStacks[par1].stackSize == 0) {
					this.furnaceItemStacks[par1] = null;
				}

				return itemstack;
			}
		}
		else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.furnaceItemStacks[slot] != null) {
			ItemStack itemstack = this.furnaceItemStacks[slot];
			this.furnaceItemStacks[slot] = null;
			return itemstack;
		}
		else {
			return null;
		}
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.furnaceItemStacks[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return this.isInvNameLocalized() ? this.field_94130_e : "container.furnace";
	}

	public boolean isInvNameLocalized() {
		return this.field_94130_e != null && this.field_94130_e.length() > 0;
	}

	public void setGuiDisplayName(String text) {
		this.field_94130_e = text;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.furnaceItemStacks.length) {
				this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.burnTime = par1NBTTagCompound.getShort("BurnTime");
		this.cookTime = par1NBTTagCompound.getShort("CookTime");
		this.currentBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

		if (par1NBTTagCompound.hasKey("CustomName")) {
			this.field_94130_e = par1NBTTagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short) this.burnTime);
		par1NBTTagCompound.setShort("CookTime", (short) this.cookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
			if (this.furnaceItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (this.isInvNameLocalized()) {
			par1NBTTagCompound.setString("CustomName", this.field_94130_e);
		}
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int i) {
		return this.cookTime * i / 200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = 200;
		}

		return this.burnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	public void updateEntity() {
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;

		if (this.burnTime > 0) {
			--this.burnTime;
		}

		if (!this.worldObj.isRemote) {
			if (this.burnTime == 0 && this.canSmelt()) {
				this.currentBurnTime = this.burnTime = getItemBurnTime(this.furnaceItemStacks[1]);

				if (this.burnTime > 0) {
					flag1 = true;

					if (this.furnaceItemStacks[1] != null) {
						--this.furnaceItemStacks[1].stackSize;

						if (this.furnaceItemStacks[1].stackSize == 0) {
							this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItemStack(furnaceItemStacks[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt()) {
				++this.cookTime;

				if (this.cookTime == 200) {
					this.cookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else {
				this.cookTime = 0;
			}

			if (flag != this.burnTime > 0) {
				flag1 = true;
				BlockGlowFurnace.updateFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1) {
			this.onInventoryChanged();
		}
	}

	private boolean canSmelt() {
		if (this.furnaceItemStacks[0] == null) return false;
		else {
			// Check if the item in the slot 1 can be smelted (has a set furnace recipe).
			ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
			if (stack == null) return false;
			if (this.furnaceItemStacks[2] == null) return true;
			if (!this.furnaceItemStacks[2].isItemEqual(stack)) return false;
			
			// Add the result of the furnace recipe to the current stack size (already smelted so far).
			int result = this.furnaceItemStacks[2].stackSize + stack.stackSize;
			
			// Make sure we aren't going over the set stack limit's size.
			return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
		}
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);

			if (this.furnaceItemStacks[2] == null) {
				this.furnaceItemStacks[2] = itemstack.copy();
			}
			else if (this.furnaceItemStacks[2].isItemEqual(itemstack)) {
				furnaceItemStacks[2].stackSize += itemstack.stackSize;
			}

			--this.furnaceItemStacks[0].stackSize;

			if (this.furnaceItemStacks[0].stackSize <= 0) {
				this.furnaceItemStacks[0] = null;
			}
		}
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null) {
			return 0;
		}
		else {
			int i = stack.getItem().itemID;
			Item item = stack.getItem();

			if (stack.getItem() instanceof ItemBlock && Block.blocksList[i] != null) {
				Block block = Block.blocksList[i];

				if (block == Block.woodSingleSlab) { return 150; }

				if (block.blockMaterial == Material.wood) { return 300; }

				if (block == Block.coalBlock) { return 16000; }
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) return 200;
			if (i == Item.stick.itemID) return 100;
			if (i == Item.coal.itemID) return 1600;
			if (i == Item.bucketLava.itemID) return 20000;
			if (i == Block.sapling.blockID) return 100;
			if (i == Item.blazeRod.itemID) return 2400;
			if (i == ExtraTools.glowCoal.itemID) return 1600 / 8 * 12;
			return GameRegistry.getFuelValue(stack);
		}
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest() {

	}

	public void closeChest() {

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
		return side != 0 || slot != 1 || stack.itemID == Item.bucketEmpty.itemID;
	}

}
