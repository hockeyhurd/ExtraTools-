package com.hockeyhurd.entity.throwable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPuck extends EntityThrowable {

	public EntityPuck(World world) {
		super(world);
	}

	public EntityPuck(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	public EntityPuck(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition movingObj) {
		if (movingObj.entityHit != null) {
			byte b0 = 0;

			if (movingObj.entityHit instanceof EntityBlaze) {
				b0 = 3;
			}

			movingObj.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) b0);
		}

		for (int i = 0; i < 8; ++i) {
			this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}

}
