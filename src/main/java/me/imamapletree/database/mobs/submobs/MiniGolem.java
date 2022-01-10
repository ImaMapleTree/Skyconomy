package me.imamapletree.database.mobs.submobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.minecraft.server.v1_16_R2.EntitySlime;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;

public class MiniGolem extends EntitySlime {
	private World world;
	
	public MiniGolem(org.bukkit.World world) {
		super(EntityTypes.SLIME, ((CraftWorld) world).getHandle());
	}
	
	public MiniGolem(Location loc, int size) {
		super(EntityTypes.SLIME, ((CraftWorld) loc.getWorld()).getHandle());
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setSize(size, true);
		this.spawn(loc);
	}
	
	public void spawn(Location loc) {
		this.world = ((CraftWorld) loc.getWorld()).getHandle();
		this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}
}
