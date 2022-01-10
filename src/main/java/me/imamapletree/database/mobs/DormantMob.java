package me.imamapletree.database.mobs;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.World;

import me.imamapletree.tools.SkyLog;

public class DormantMob {
	private Class<?> mobClass;
	private World world;
	private String name;
	private int tier;
	private int number = 1;
	
	
	public DormantMob(Class<?> mobClass, World world, String name, int tier, int number) {
		this.mobClass = mobClass;
		this.world = world;
		this.name = name;
		this.tier = tier;
		this.number = number;
	}
	
	public DormantMob(Class<?> mobClass, World world, String name, int tier) {
		this.mobClass = mobClass;
		this.world = world;
		this.name = name;
		this.tier = tier;
	}
	
	public Class<?> getMobClass() {
		return this.mobClass;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getTier() {
		return this.tier;
	}
	
	public int getAmount() {
		return this.number;
	}
	
	public CustomBoss constructMob(World world2) {
		try {
			return (CustomBoss) this.mobClass.getConstructor(World.class, String.class, int.class).newInstance(world2, this.name, this.tier);
		} catch (InstantiationException e) {
			SkyLog.slog(e);
			return null;
		} catch (IllegalAccessException e) {
			SkyLog.slog(e);
			return null;
		} catch (IllegalArgumentException e) {
			SkyLog.slog(e);
			return null;
		} catch (InvocationTargetException e) {
			SkyLog.slog(e);
			SkyLog.slog("------------------");
			SkyLog.slog(e.getCause());
			return null;
		} catch (NoSuchMethodException e) {
			SkyLog.slog(e);
			return null;
		} catch (SecurityException e) {
			SkyLog.slog(e);
			return null;
		}
	}

	
	public CustomBoss constructMob() {
		return constructMob(this.world);
	}
}
