package me.imamapletree.database.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.imamapletree.api.SmartBossBar;
import me.imamapletree.database.mobs.submobs.MiniGolem;
import me.imamapletree.database.mobs.submobs.SubBoss;
import net.minecraft.server.v1_16_R2.EntitySlime;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;

public class SlimeGolem extends EntitySlime implements CustomBoss {
	private SmartBossBar bar;
	private World world;
	private BossType type = BossType.SlimeGolem;
	private List<SubBoss> subBosses = new ArrayList<SubBoss>();
	
	public SlimeGolem(org.bukkit.World world) {
		super(EntityTypes.SLIME, ((CraftWorld) world).getHandle());
	}
	
	public SlimeGolem(org.bukkit.World world, String name, int tier) {
		super(EntityTypes.SLIME, ((CraftWorld) world).getHandle());
	}
	
	public SlimeGolem(World world, int size) {
		super(EntityTypes.SLIME, world);
		this.setSize(size, true);
	}
	
	public void spawn(Location loc) {
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
		this.setSize(4, true);
		MiniGolem SG1 = new MiniGolem(loc, 3);
		MiniGolem SG2 = new MiniGolem(loc, 2);
		SG2.startRiding(SG1);
		SG1.startRiding(this);
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
	
	public void setTargetRange(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(f);
	}

	public SmartBossBar getBar() {
		return this.bar;
	}
	
	public void addSubBoss(SubBoss entity) {
		this.subBosses.add(entity);
	}
	
	public CraftEntity getEntity() {
		return super.getBukkitEntity();
	}
}
