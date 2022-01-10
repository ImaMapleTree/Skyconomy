package me.imamapletree.database.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.imamapletree.api.SmartBossBar;
import me.imamapletree.api.addons.PatherfinderGoalDamageClosestHuman;
import me.imamapletree.database.mobs.submobs.HorseJockey;
import me.imamapletree.database.mobs.submobs.SubBoss;
import me.imamapletree.tools.SkyLog;
import net.minecraft.server.v1_16_R2.EntityAIBodyControl;
import net.minecraft.server.v1_16_R2.EntityHorse;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R2.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_16_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_16_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R2.PathfinderGoalRandomStroll;

public class Horsemen extends EntityHorse implements CustomBoss {

	private SmartBossBar bar;
	private Float damage = 4.0f;
	private BossType type = BossType.GiantBoss;
	private List<SubBoss> subBosses = new ArrayList<SubBoss>();
	
	public Horsemen(org.bukkit.World world) {
		super(EntityTypes.HORSE, ((CraftWorld) world).getHandle());
	}
	
	public Horsemen(org.bukkit.World world, String name, int tier) {
		super(EntityTypes.HORSE, ((CraftWorld) world).getHandle());
		//this.setGoals();
		this.setAttackDamage(4.0f);
		this.setMovementSpeed(0.45f);
		this.setArmor(10);
		this.setArmorToughness(5);
		this.setHP(180);
		this.setKnockbackResistance(10);
		this.setTargetRange(64);
	}
	
	public void spawn(Location loc) {
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
		HorseJockey HJ = new HorseJockey(loc);
		HJ.startRiding(this);
	}
	
	public void tick() {
		super.tick();
		if(this.dead) {
			for (SubBoss entity : this.subBosses) {
				entity.getNestedEntity().killEntity();
			}
		}
	}
	
	public void attachBossBar(SmartBossBar bar) {
		this.bar = bar;
		this.bar.addEntityTracker(this);
	}
	
	public BossType getType() {
		return this.type;
	}
	
	public void addHealth(float f) {
		Float health = this.getHealth() + f;
		if(health > this.getMaxHealth()) this.setMaxHealth(health);
		this.setHealth(health);
		if(this.bar != null) this.bar.addEntityTracker(this);
	}
	
	public void addAttackDamage(float f) {
		this.damage += f;
	}
	
	public void setHP(float f) {
		if(f >= this.getMaxHealth()) this.setMaxHealth(f);
		this.setHealth(f);
		if(this.bar != null) this.bar.addEntityTracker(this);
	}
	
	public void setMaxHealth(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(f);
		if(this.bar != null) this.bar.addEntityTracker(this);
	}
	
	public void setAttackDamage(float f) {
		this.damage = f;
	}
	
	public void setMovementSpeed(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(f);
	}
	
	public void setAttackKnockback(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(f);
	}
	
	public void setArmorToughness(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(f);
	}
	
	public void setArmor(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(f);
	}
	
	public void setKnockbackResistance(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(f);
	}
	
	public void setTargetRange(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(f);
	}
	
	@Override 
	protected EntityAIBodyControl r() {
        return super.r();
	}
	
	public void addSubBoss(SubBoss entity) {
		this.subBosses.add(entity);
	}
	
	public SmartBossBar getBar() {
		return this.bar;
	}
	
	public CraftEntity getEntity() {
		return super.getBukkitEntity();
	}
	
	protected void setGoals() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PatherfinderGoalDamageClosestHuman(this, 1.0D, this.damage));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false, 0, null));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 20F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
	}
}
