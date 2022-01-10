package me.imamapletree.tools;

import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.api.InventoryCoordinate;
import me.imamapletree.api.WorldCoordinate;

public class CoordinateParse {
	public static InventoryCoordinate InventoryCoordParse(String CString) {
		int columns;
		int rows;
		if(CString.contains("!")) {
			String[] split_coords = CString.split("!");
			try {
				columns = Integer.valueOf(split_coords[0]);
				rows = Integer.valueOf(split_coords[1]);
			} catch (Error e) {
				return null;
			}
			InventoryCoordinate coords = new InventoryCoordinate(columns, rows);
			return coords;
		}
		else {return null;}
		}
	
	public static WorldCoordinate WorldCoordParse(String CString) {
		int x;
		int y;
		int z;
		if(CString.contains("!")) {
			String[] split_coords = CString.split("!");
			try {
				x = Integer.valueOf(split_coords[0]);
				y = Integer.valueOf(split_coords[1]);
				z = Integer.valueOf(split_coords[2]);
			} catch (Error e) {
				return null;
			}
			WorldCoordinate coords = new WorldCoordinate(x, y, z);
			return coords;
		}
		else {return null;}
		}
	
	public static FlatCoordinate FlatCoordParse(String CString) {
		int x;
		int z;
		if(CString.contains("!")) {
			String[] split_coords = CString.split("!");
			try {
				x = Integer.valueOf(split_coords[0]);
				z = Integer.valueOf(split_coords[1]);
			} catch (Error e) {
				return null;
			}
			FlatCoordinate coords = new FlatCoordinate(x, z);
			return coords;
		}
		else {return null;}
	}
	
	public final static String toString(InventoryCoordinate coords) {
		String x = String.valueOf(coords.getX());
		String y = String.valueOf(coords.getZ());
		String CString = x + "!" + y;
		return CString;
	}
	
	public final static String toString(WorldCoordinate coords) {
		String x = String.valueOf(coords.getX());
		String y = String.valueOf(coords.getY());
		String z = String.valueOf(coords.getZ());
		String CString = x + "!" + y + "!" + z;
		return CString;
	}

	public final static String toString(FlatCoordinate coords) {
		if(coords == null) return "none";
		String x = String.valueOf(coords.getX());
		String z = String.valueOf(coords.getZ());
		String CString = x + "!" + z;
		return CString;
	}
}

