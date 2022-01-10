package me.imamapletree.api;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

public class WorldCoordinate implements Coordinate, Cloneable
{
	protected int X;
	protected int Y;
	protected int Z;
	
	public WorldCoordinate(final int X, final int Y, final int Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}
	
	public WorldCoordinate(Location loc) {
		this.X = loc.getBlockX();
		this.Y = loc.getBlockY();
		this.Z = loc.getBlockZ();
	}
	
	public WorldCoordinate(WorldCoordinate WC) {
		this.X = WC.getX();
		this.Y = WC.getY();
		this.Z = WC.getZ();
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public int getZ() {
		return this.Z;
	}
	
	public WorldCoordinate add(int X, int Y, int Z) {
		this.X += X;
		this.Y += Y;
		this.Z += Z;
		return this;
	}
	
	public WorldCoordinate subtract(int X, int Y, int Z) {
		this.X -= X;
		this.Y -= Y;
		this.Z -= Z;
		return this;
	}
	
	public ChunkCoordinate getChunkCoords() {
		int tX = 0;
		int tZ = 0;
		
		tX = (int) Math.ceil(this.X/16);
		tZ = (int) Math.ceil(this.Z/16);
		//tX = Math.round(this.X/16);
		//tZ = Math.round(this.Z/16);
		//if(this.X > 0) tX = (int) Math.floor(this.X/16);
		//if(this.Z > 0) tZ = (int) Math.floor(this.Z/16);
		//if(this.X < 0) tX = (int) Math.ceil(this.X/16);
		//if(this.Z < 0) tZ = (int) Math.floor(this.Z/16);
		return new ChunkCoordinate(tX, tZ);
	}
	
	public static Location createLocation(World world, WorldCoordinate WC) {
		return new Location(world, WC.getX(), WC.getY(), WC.getZ());
	}
	
	public static Location createLocation(World world, FlatCoordinate IC) {
		return new Location(world, IC.getX(), new Random().nextInt(255), IC.getZ());
	}
	
	public static Location createLocation(World world, FlatCoordinate IC, int Y) {
		return new Location(world, IC.getX(), Y, IC.getZ());
	}
	
	public WorldCoordinate clone() {
		return new WorldCoordinate(this);
	}
	
	@Override
	public String toString() {
		return "WorldCoordinate [X=" + String.valueOf(this.X) + ", Y=" + String.valueOf(this.Y) + ", Z=" + String.valueOf(this.Z) + "]";
	}
}
