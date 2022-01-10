package me.imamapletree.database.mobs.submobs;

import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityLiving;

public interface SubBoss {
	public void setParent(Entity e);
	public EntityLiving getNestedEntity();
	public void setHP(float f);
	public void setMaxHealth(float f);
	public boolean startRiding(Entity e);
}
