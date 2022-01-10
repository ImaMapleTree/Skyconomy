package me.imamapletree.api;

import org.bukkit.Location;

public class ChunkCoordinate implements Coordinate, Cloneable
{
	protected int X;
	protected int Y;
	protected int Z;
	
	public ChunkCoordinate(final int X, final int Z) {
		this.X = X;
		this.Z = Z;
	}
	
	public ChunkCoordinate(Location loc) {
		this.X = loc.getBlockX();
		this.Z = loc.getBlockZ();
	}
	
	public ChunkCoordinate(WorldCoordinate WC) {
		this.X = WC.getX();
		this.Z = WC.getZ();
	}
	
	public ChunkCoordinate(ChunkCoordinate CC) {
		this.X = CC.getX();
		this.Z = CC.getZ();
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getZ() {
		return this.Z;
	}
	
	public int getRealX() {
		return (int) this.X * 16;
	}
	
	public int getRealZ() {
		return (int) this.Z * 16;
	}
	
	public ChunkCoordinate add(int X, int Z) {
		this.X += X;
		this.Z += Z;
		return this;
	}
	
	public ChunkCoordinate subtract(int X, int Z) {
		this.X -= X;
		this.Z -= Z;
		return this;
	}
	
	public WorldCoordinate getWorldCoords() {
		int tX = (int) this.X * 16;
		int tZ = (int) this.Z * 16;
		return new WorldCoordinate(tX, 0, tZ);
	}
	
	public WorldCoordinate getWorldCoords(int Y) {
		int tX = (int) this.X * 16;
		int tZ = (int) this.Z * 16;
		return new WorldCoordinate(tX, Y, tZ);
	}
	
	public ChunkCoordinate clone() {
		return new ChunkCoordinate(this);
	}
	
	@Override
	public String toString() {
		return "ChunkCoordinate [X=" + String.valueOf(this.X) + ", Z=" + String.valueOf(this.Z) + "]";
	}
}
