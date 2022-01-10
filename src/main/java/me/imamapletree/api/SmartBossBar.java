package me.imamapletree.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.imamapletree.tools.SkyLog;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R2.EntityLiving;

public class SmartBossBar {
	private BossBar bar;
	private List<Player> viewers = new ArrayList<Player>();
	private Map<String, Float> maxHealths =  new HashMap<String, Float>();
	private Map<String, Float> healths = new HashMap<String, Float>();
	private Float progress = 0f;
	private Float progressMax = 0f;
	
	public SmartBossBar(String name, BarStyle style, int tier) {
		BarColor color;
		ChatColor tc;
		if(tier==0) {color = BarColor.GREEN; tc = ChatColor.GREEN;}
		else if(tier==1) {color = BarColor.BLUE; tc = ChatColor.BLUE;}
		else if(tier==2) {color = BarColor.PURPLE; tc = ChatColor.DARK_PURPLE;}
		else {color = BarColor.YELLOW; tc = ChatColor.GOLD;}
		this.bar = Bukkit.createBossBar(tc + "" + ChatColor.BOLD + name, color, style, BarFlag.PLAY_BOSS_MUSIC);
	}
	
	public SmartBossBar(String name, BarColor color, BarStyle style, BarFlag flag) {
		this.bar = Bukkit.createBossBar(name, color, style, flag);
	}
	
	public void addEntityTracker(LivingEntity e) {
		Double MaxHealth = e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		Double Health = e.getHealth();
		String uuid = e.getUniqueId().toString();
		if(this.maxHealths.containsKey(uuid)) {
			Float e2Health = this.healths.get(uuid);
			Float e2MaxHealth = this.maxHealths.get(uuid);
			this.progress -= e2Health;
			this.progressMax -= e2MaxHealth;
			this.healths.put(uuid, Health.floatValue());
			this.maxHealths.put(uuid, MaxHealth.floatValue());
			this.progress += Health.floatValue();
			this.progressMax += MaxHealth.floatValue();
		} else {
			this.addProgress(Health.floatValue());
			this.healths.put(uuid, Health.floatValue());
			this.maxHealths.put(uuid, MaxHealth.floatValue());
		}
		this.update();
	}
	
	public void addEntityTracker(EntityLiving e) {
		LivingEntity LE = (LivingEntity) e.getBukkitEntity();
		String uuid = e.getUniqueID().toString();
		if(this.maxHealths.containsKey(uuid)) {
			this.addEntityTracker(LE);
		} else {
			Float Health = e.getHealth();
			Float MaxHealth = e.getMaxHealth();
			this.addProgress(Health.floatValue());
			this.healths.put(uuid, e.getHealth());
			this.maxHealths.put(uuid, MaxHealth.floatValue());
			this.update();
		}
	}
	
	public void removeEntityTracker(LivingEntity e, boolean delete) {
		String uuid = e.getUniqueId().toString();
		if(this.maxHealths.containsKey(uuid)) {
			Double Health = e.getHealth();
			this.removeProgress(Health.floatValue());
			if(delete) {
				Float MaxHealth = this.maxHealths.get(uuid);
				this.removeMaxProgress(MaxHealth.floatValue());
			}
			this.maxHealths.remove(uuid);
			this.healths.remove(uuid);
		}
	}
	
	public void removeEntityTracker(EntityLiving e, boolean delete) {
		LivingEntity LE = (LivingEntity) e.getBukkitEntity();
		this.removeEntityTracker(LE, delete);
	}
	
	public void removeEntityTracker(LivingEntity e) {
		this.removeEntityTracker(e, false);
	}
	
	public void removeEntityTracker(EntityLiving e) {
		LivingEntity LE = (LivingEntity) e.getBukkitEntity();
		this.removeEntityTracker(LE, false);
	}
	
	public void addProgress(Float f) {
		SkyLog.slog("Adding: " + f);
		Float addition = f + this.progress;
		if(addition >= this.progressMax) setPMax(addition);
		this.progress = addition;
		this.update();
	}
	
	public void removeProgress(Float f) {
		SkyLog.slog("Removing: " + f);
		this.progress -= f;
		if(this.progress <= 0) {
			this.viewers.clear();
			this.bar.removeAll();
			this.progress = 0f;
		}
		this.update();
	}
	
	public void removeMaxProgress(Float f) {
		this.progressMax -= f;
		if(this.progressMax <= 0) {
			this.viewers.clear();
			this.bar.removeAll();
			this.progressMax = 1f;
		}
		this.update();
	}
	
	public void setProgress(Float f) {
		if(f >= this.progressMax) setPMax(f);
		this.progress = f;
		this.update();
	}
	
	public void setPMax(Float f) {
		this.progressMax = f;
		this.update();
	}
	
	public void showBar(List<Player> players) {
		for(Player player : players) {
			this.bar.addPlayer(player);
		}
		this.viewers.addAll(players);
	}
	
	public void update() {
		this.bar.setProgress(this.progress/this.progressMax);
	}
	
	public List<Player> getViewers() {
		return this.viewers;
	}
	
	public BossBar getRoot() {
		return this.bar;
	}
	
	public void removeAll() {
		this.bar.removeAll();
	}
}

