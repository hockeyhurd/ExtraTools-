package com.hockeyhurd.entity.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTickTorch extends TileEntity {

	private final int[] AREA_SIZE = new int[] {
			1, 3, 5, 7, 9
	};

	private final int TICK_BONUS = 1;
	private byte mode, defaultMode;
	private AxisAlignedBB boundBox;
	private boolean active;
	private Random random;

	public TileEntityTickTorch() {
		this.active = true;
		this.defaultMode = -1;

		this.random = new Random();
	}

	public void updateEntity() {
		if (worldObj.isRemote || !active) return;
		
		if (defaultMode != mode) {
			boundBox = AxisAlignedBB.getBoundingBox(this.xCoord - AREA_SIZE[mode], this.yCoord - AREA_SIZE[mode], this.zCoord - AREA_SIZE[mode], this.xCoord + AREA_SIZE[mode], this.yCoord + AREA_SIZE[mode], this.zCoord + AREA_SIZE[mode]);
			defaultMode = mode;
		}
		
		for (int y = (int) boundBox.maxY; y > boundBox.minY; y--) {
			for (int x = (int) boundBox.minX; x < boundBox.maxX; x++) {
				for (int z = (int) boundBox.minZ; z < boundBox.maxZ; z++) {
					Block current = this.worldObj.getBlock(x, y, z);
					if (current == null || current == Blocks.air) continue;
					
					if (current.getTickRandomly()) {
						for (int i = 0; i < TICK_BONUS; i++) current.updateTick(this.worldObj, x, y, z, random);
					}
					
					TileEntity ent = this.worldObj.getTileEntity(x, y, z);
					if (ent != null && !(ent instanceof TileEntityTickTorch)) {
						for (int i = 0; i < TICK_BONUS; i++) ent.updateEntity();
					}
				}
			}
		}
	}
	
	public boolean updateMode(boolean sneak) {
		if (mode >= AREA_SIZE.length - 1 && !sneak) return false;
		else if (mode == 0 && sneak) return false;
		
		mode += sneak ? -1 : 1;
		return true;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getMode() {
		final int size = AREA_SIZE[mode];
		return size + "x" + size + "x" + size;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound comp) {
		super.writeToNBT(comp);
		comp.setByte("Mode", mode);
		comp.setBoolean("Active", active);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound comp) {
		super.readFromNBT(comp);
		this.mode = comp.getByte("Mode");
		this.active = comp.getBoolean("Active");
	}

}
