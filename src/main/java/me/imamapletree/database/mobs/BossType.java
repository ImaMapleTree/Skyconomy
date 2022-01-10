package me.imamapletree.database.mobs;

import net.minecraft.server.v1_16_R2.EntityTypes;

public enum BossType {
	SlimeGolem(EntityTypes.SLIME, SlimeGolem.class, 0),
	SpiderQueen(EntityTypes.SPIDER, SpiderBoss.class, 2),
	GiantBoss(EntityTypes.GIANT, GiantBoss.class, 1),
	Horsemen(EntityTypes.HORSE, Horsemen.class, 2);
	
	private EntityTypes<?> entitytypes;
	private Class<?> cl;
	private int tier;
	
	private BossType(EntityTypes<?> entitytypes, Class<?> cl, int tier) {
		this.entitytypes = entitytypes;
		this.cl = cl;
		this.tier = tier;
	}
	
	public EntityTypes<?> getEntityType() {
		return this.entitytypes;
	}
	
	public Class<?> getCustomClass() {
		return this.cl;
	}
	
	public int getTier() {
		return this.tier;
	}
}
