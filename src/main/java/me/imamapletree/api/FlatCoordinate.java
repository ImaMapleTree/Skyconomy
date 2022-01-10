package me.imamapletree.api;

import org.bukkit.Location;

public class FlatCoordinate implements Coordinate, Cloneable {
	private int X;
	private int Z;
	
	public FlatCoordinate(int X, int Z) {
		this.X = X;
		this.Z = Z;
	}
	
	public FlatCoordinate(Location loc) {
		this.X = loc.getBlockX();
		this.Z = loc.getBlockZ();
	}
	
	public FlatCoordinate(WorldCoordinate WC) {
		this.X = WC.getX();
		this.Z = WC.getZ();
	}
	
	private FlatCoordinate(FlatCoordinate IC) {
		this.X = IC.getX();
		this.Z = IC.getZ();
	}
	
	public FlatCoordinate add(int X, int Z) {
		this.X += X;
		this.Z += Z;
		return this;
	}
	
	public FlatCoordinate subtract(int x, int z) {
		this.X = this.X - x;
		this.Z = this.Z - z;
		return this;
	}
	
	public FlatCoordinate addX(int X) {
		this.X += X;
		return this;
	}
	
	public FlatCoordinate subtractX(int X) {
		this.X -= X;
		return this;
	}
	
	public FlatCoordinate addZ(int Z) {
		this.Z += Z;
		return this;
	}
	
	public FlatCoordinate subtractZ(int Z) {
		this.Z -= Z;
		return this;
	}
	
	public FlatCoordinate addX(int X, boolean New) {
		if(New) {
			FlatCoordinate temp = new FlatCoordinate(this);
			temp.X += X;
			return temp;
		}
		this.X += X;
		return this;
	}
	
	public FlatCoordinate subtractX(int X, boolean New) {
		if(New) {
			FlatCoordinate temp = new FlatCoordinate(this);
			temp.X -= X;
			return temp;
		}
		this.X -= X;
		return this;
	}
	
	public FlatCoordinate addZ(int Z, boolean New) {
		if(New) {
			FlatCoordinate temp = new FlatCoordinate(this);
			temp.Z += Z;
			return temp;
		}
		this.Z += Z;
		return this;
	}
	
	public FlatCoordinate subtractZ(int Z, boolean New) {
		if(New) {
			FlatCoordinate temp = new FlatCoordinate(this);
			temp.Z -= Z;
			return temp;
		}
		this.Z -= Z;
		return this;
	}
	
	public boolean isGreaterX(FlatCoordinate IC) {
		return this.getX() > IC.getX();
	}
	
	public boolean isGreaterZ(FlatCoordinate IC) {
		return this.getZ() > IC.getZ();
	}
	
	public boolean isLessX(FlatCoordinate IC) {
		return this.getX() < IC.getX();
	}
	
	public boolean isLessZ(FlatCoordinate IC) {
		return this.getZ() < IC.getZ();
	}
	
	public boolean isGreater(FlatCoordinate IC) {
		return this.getX() + this.getZ() > IC.getX() + IC.getZ();
	}
	
	public boolean isLess(FlatCoordinate IC) {
		return this.getX() + this.getZ() < IC.getX() + IC.getZ();
	}
	
	public boolean equalsX(FlatCoordinate IC) {
		return IC.getX() == this.getX();
	}
	
	public boolean equalsZ(FlatCoordinate IC) {
		return IC.getZ() == this.getZ();
	}
	
	public boolean equals(FlatCoordinate IC) {
		return IC.getX() == this.getX() && IC.getZ() == this.getZ();
	}
	
	@Override
	public int getX() {
		return this.X;
	}

	@Override
	public int getZ() {
		return this.Z;
	}
	
	public ChunkCoordinate getChunkCoords() {
		int tX = (int) Math.floor(this.X/16);
		int tZ = (int) Math.floor(this.Z/16);
		return new ChunkCoordinate(tX, tZ);
	}
	
	public FlatCoordinate clone() {
		return new FlatCoordinate(this);
	}
	
	public String string() {
		return "(" + String.valueOf(this.X) + "," + String.valueOf(this.Z) + ")";
	}

}
