package me.imamapletree.database.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.Vector;

import me.imamapletree.api.SmartBossBar;
import me.imamapletree.database.mobs.submobs.SubBoss;
import me.imamapletree.tools.SkyLog;
import net.minecraft.server.v1_16_R2.DamageSource;
import net.minecraft.server.v1_16_R2.EntityAIBodyControl;
import net.minecraft.server.v1_16_R2.EntityArrow;
import net.minecraft.server.v1_16_R2.EntityGiantZombie;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.EntityVillager;
import net.minecraft.server.v1_16_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R2.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_16_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_16_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R2.PathfinderGoalRandomStroll;

public class GiantBoss extends EntityGiantZombie implements CustomBoss {

	private BossType type = BossType.GiantBoss;
	private boolean isInSlam = false;
	private boolean isInCharge = false;
	private SmartBossBar bar;
	private List<SubBoss> subBosses = new ArrayList<SubBoss>();
	
	public GiantBoss(org.bukkit.World world) {
		super(EntityTypes.GIANT, ((CraftWorld) world).getHandle());
	}
	
	public GiantBoss(org.bukkit.World world, String name, int tier) {
		super(EntityTypes.GIANT, ((CraftWorld) world).getHandle());
		this.setGoals();
		this.setMovementSpeed(0.3f);
		this.setAttackDamage(10);
		this.setArmor(10);
		this.setArmorToughness(4);
		this.setHP(180);
		this.setKnockbackResistance(10);
		this.setTargetRange(64);
	}
	
	public void tick() {
        super.tick();
        if (this.isInSlam && this.onGround) {
            final LivingEntity giant = (LivingEntity)this.getBukkitEntity();
            for (final Entity e : giant.getNearbyEntities(6.0, 6.0, 6.0)) {
                if (giant.getLocation().distance(giant.getLocation()) < 6.0) {
                    e.setVelocity(new Vector(0.0, 1.6, 0.0));
                }
            }
            giant.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, giant.getLocation().subtract(0.0, 1.0, 0.0), 50, 6.0, 0.0, 6.0);
            giant.getWorld().playSound(giant.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.5f, 0.5f);
            this.isInSlam = false;
        }
        if (this.isInCharge) {
        	this.isInCharge = false;
        }
    }
	
	
	public boolean damageEntity(final DamageSource damagesource, final float f) {
		final net.minecraft.server.v1_16_R2.Entity entity = damagesource.j();
		EntityLiving damager = null;
		if(damagesource.j() instanceof EntityLiving) {
			damager = (EntityLiving) damagesource.j();
		}
    	if (damagesource.j() instanceof EntityArrow) {
			EntityArrow EA = (EntityArrow) damagesource.j();
			if(EA.getShooter() != null) damager = (EntityLiving) EA.getShooter();
    	}
    	final LivingEntity giant = (LivingEntity)this.getBukkitEntity();
    	if (!this.isInSlam && this.getRandom().nextInt(20) == 1) {
			giant.setVelocity(new Vector(0, 1, 0));
        	giant.setFallDistance(-20.0f);
        	giant.getWorld().playSound(giant.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.5f, 1.0f);
        	giant.getWorld().playSound(giant.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.5f, 1.0f);
        	giant.getWorld().playSound(giant.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.5f, 1.0f);
        	giant.getWorld().playSound(giant.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.5f, 1.0f);
        	giant.getWorld().playSound(giant.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.5f, 1.0f);
        	this.isInSlam = true;
        	return false;
    	}
    	else if (!this.isInCharge && this.getRandom().nextInt(2) == 1 && damager != null) {
    		
    		final LivingEntity attacker = (LivingEntity)(damager.getBukkitEntity());
    		Double distance = attacker.getLocation().distance(giant.getLocation());
    		SkyLog.log(distance);
    		if(distance <= 8) {
    			super.damageEntity(damagesource, f);
    			return true;
    		}
    		Vector target = attacker.getLocation().toVector();
    		Vector velocity = target.subtract(giant.getLocation().toVector());
    		giant.setVelocity(velocity.normalize().add(new Vector(0, 0.2, 0)).multiply(distance/5));
    		this.isInCharge = false;
    		return false;
    	}
    	super.damageEntity(damagesource, f);
    	return true;
	}
	
	public void spawn(Location loc) {
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
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
		Attributable attr = (Attributable) this.getBukkitEntity();
		AttributeInstance attrI = attr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
		Double Attack = attrI.getValue() + f;
		attrI.setBaseValue(Attack);
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
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(f);
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
	
	public void addSubBoss(SubBoss entity) {
		this.subBosses.add(entity);
	}
	
	public SmartBossBar getBar() {
		return this.bar;
	}
	
	public CraftEntity getEntity() {
		return super.getBukkitEntity();
	}
	
	@Override 
	protected EntityAIBodyControl r() {
        return super.r();
	}
	
	protected void setGoals() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 0.5D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false, 0, null));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityVillager>(this, EntityVillager.class, false));
	}
}
