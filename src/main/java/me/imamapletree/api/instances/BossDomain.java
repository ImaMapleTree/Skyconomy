package me.imamapletree.api.instances;

public class BossDomain {
	private String name;
	private int x;
	private int y;
	private int z;
	
	public BossDomain(String name, int x, int y, int z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
}
