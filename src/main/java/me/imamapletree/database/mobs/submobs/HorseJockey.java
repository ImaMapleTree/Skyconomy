package me.imamapletree.database.mobs.submobs;

import org.bukkit.Location;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.imamapletree.database.mobs.CustomBoss;
import net.minecraft.server.v1_16_R2.DamageSource;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntitySkeletonWither;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.EnumItemSlot;
import net.minecraft.server.v1_16_R2.ItemStack;
import net.minecraft.server.v1_16_R2.Items;
import net.minecraft.server.v1_16_R2.World;

public class HorseJockey extends EntitySkeletonWither implements SubBoss {
	private World world;
	private Entity parent;
	
	public HorseJockey(org.bukkit.World world) {
		super(EntityTypes.WITHER_SKELETON, ((CraftWorld) world).getHandle());
	}
	
	public HorseJockey(Location loc) {
		super(EntityTypes.WITHER_SKELETON, ((CraftWorld) loc.getWorld()).getHandle());
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
		this.setAttackDamage(0.9f);
		this.setHP(180f);
		this.spawn(loc);
	}
	
	public boolean damageEntity(final DamageSource damagesource, final float f) {
		if(this.parent != null) {this.parent.damageEntity(damagesource, f);}
		super.damageEntity(damagesource, f);
		return true;
	}
	
	public void spawn(Location loc) {
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}
	
	public void setAttackDamage(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(f);
	}
	
	public void setParent(Entity e) {
		this.parent = e;
	}
	
	public EntityLiving getNestedEntity() {
		return this;
	}
	
	public void setHP(float f) {
		if(f >= this.getMaxHealth()) this.setMaxHealth(f);
		this.setHealth(f);
	}
	
	public void setMaxHealth(float f) {
		Attributable attr = (Attributable) this.getBukkitEntity();
		attr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(f);
	}
	
	public boolean startRiding(Entity e) {
		if(CustomBoss.class.isInstance(e)) {
			CustomBoss cb = (CustomBoss) e;
			cb.addHealth(this.getMaxHealth());
			this.parent = e;
			cb.addSubBoss(this);
			this.setHP(99999);
		}
		return super.startRiding(e);
	}
}
