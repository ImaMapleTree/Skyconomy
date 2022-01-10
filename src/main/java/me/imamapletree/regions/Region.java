package me.imamapletree.regions;


import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import me.imamapletree.api.ChunkCoordinate;
import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;

public class Region {
	@SuppressWarnings("unused")
	private World world;
	private FlatCoordinate corner1;
	private FlatCoordinate corner2;
	private Vector p1;
	private Vector p2;
	private int XSteps;
	private int ZSteps;
	private ChunkCoordinate[] chunks = {};
	
	public Region(WorldCoordinate WC1, WorldCoordinate WC2) {
		FlatCoordinate FC1 = new FlatCoordinate(WC1);
		FlatCoordinate FC2 = new FlatCoordinate(WC2);
		this.corner1 = FC1;
		this.corner2 = FC2;
		this.registerChunks();
	}
	
	public Region(FlatCoordinate FC1, FlatCoordinate FC2) {
		this.corner1 = FC1;
		this.corner2 = FC2;
		this.registerChunks();
	}
	
	public Region(Location loc1, Location loc2) {
		if (loc1.getWorld() != loc2.getWorld()) {
			SkyLog.log(new Error("Region corners aren't in the same world!"));
			return;
		}
		this.world = loc1.getWorld();
		this.corner1 = new FlatCoordinate(loc1);
		this.corner2 = new FlatCoordinate(loc2);
		this.registerChunks();
	}
	
	public boolean contains(Location loc) {
		if(loc == null) {
			return false;
		}
		return loc.getBlockX() >= p1.getBlockX() && loc.getBlockX() <= p2.getBlockX()
				&& loc.getBlockY() >= p1.getBlockY() && loc.getBlockY() <= p2.getBlockY()
				&& loc.getBlockZ() >= p1.getBlockZ() && loc.getBlockZ() <= p2.getBlockZ();
	}
	
	public int getChunkAmountX() {
		return this.XSteps + 1;
	}
	
	public int getChunkAmountZ() {
		return this.ZSteps + 1;
	}
	
	public ChunkCoordinate[] getChunks() {
		return this.chunks;
	}
	
	private void registerChunks() {
		int distanceX;
		int distanceZ;
		ChunkCoordinate start = corner1.getChunkCoords();
		int x1 = corner1.getX();
		int x2 = corner2.getX();
		int z1 = corner1.getZ();
		int z2 = corner2.getZ();
		if(x1 > x2) distanceX = x1 - x2;
		else distanceX = x2 - x1;
		if(z1 > z2) distanceZ = z1 - z2;
		else distanceZ = z2 - z1;
		int cdX = (int) Math.ceil(distanceX/16);
		int cdZ = (int) Math.ceil(distanceZ/16);
		
		int opt1 = 0;
		int opt2 = 0;
		ChunkCoordinate new_coord;
		for(int i = 0; i < cdX; i++) {
			if(x2 > x1) opt1 = i;
			else opt1 = i * -1;
			new_coord = start.clone().add(opt1, 0);
			this.chunks = (ChunkCoordinate[]) ArrayUtils.add(this.chunks, new_coord);
			for(int o = 0; o < cdZ; o++) {
				if(z2 > z1) opt2 = o;
				else opt2 = o * -1;
				new_coord = start.clone().add(opt1, opt2);
				this.chunks = (ChunkCoordinate[]) ArrayUtils.add(this.chunks, new_coord);
			}
			
		} return;
		
	}
	
	@SuppressWarnings("unused")
	private void registerChunks2() {
		ChunkCoordinate Chunk1 = corner1.getChunkCoords();
		ChunkCoordinate Chunk2 = corner2.getChunkCoords();
		ChunkCoordinate StartingPoint;
		if (Chunk1.getX() + Chunk1.getZ() > Chunk2.getX() + Chunk2.getZ()) {
			StartingPoint = Chunk2;
			this.XSteps = Chunk1.getX() - Chunk2.getX();
			this.ZSteps = Chunk1.getZ() - Chunk2.getZ();
		} else {
			StartingPoint = Chunk1;
			this.XSteps = Chunk2.getX() - Chunk1.getX();
			this.ZSteps = Chunk2.getZ() - Chunk1.getZ();
		}
		if(this.XSteps > 64 || this.ZSteps > 64) {
			SkyLog.log(new Error("Selected region is bigger than 64x64"));
			return;
		}
		int XStepsC = XSteps;
		int ZStepsC = ZSteps;
		for (int i = 0; i < Utility.makePositive(this.XSteps)+1; i++) {
			this.chunks = (ChunkCoordinate[]) ArrayUtils.add(this.chunks, StartingPoint.clone().add(XStepsC, 0));
			if(XStepsC < 0) {XStepsC -= 1;}
			else {XStepsC += 1;}
			for (int j = 0; j < Utility.makePositive(this.ZSteps); j++) {
				this.chunks = (ChunkCoordinate[]) ArrayUtils.add(this.chunks, StartingPoint.clone().add(XStepsC, ZStepsC));
				if(ZStepsC < 0) {ZStepsC -= 1;}
				else {ZStepsC += 1;}
			}
		}
		return;
	}
}
