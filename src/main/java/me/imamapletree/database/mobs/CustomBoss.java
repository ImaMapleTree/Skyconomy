package me.imamapletree.database.mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import me.imamapletree.api.SmartBossBar;
import me.imamapletree.database.mobs.submobs.SubBoss;

public interface CustomBoss {
	public void spawn(Location loc);
	public BossType getType();
	public void setHP(float f);
	public void setMaxHealth(float f);
	public void setAttackDamage(float f);
	public void setMovementSpeed(float f);
	public void setAttackKnockback(float f);
	public void setArmorToughness(float f);
	public void setArmor(float f);
	public void setTargetRange(float f);
	public void addHealth(float f);
	public void addAttackDamage(float f);
	public void attachBossBar(SmartBossBar bar);
	public SmartBossBar getBar();
	public CraftEntity getEntity();
	public void addSubBoss(SubBoss e);
}
