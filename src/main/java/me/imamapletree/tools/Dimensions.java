package me.imamapletree.tools;

import org.bukkit.Location;

public class Dimensions {
	
	public static int[] getDimensions(Location[] corners) {
	    if (corners.length != 2) throw new IllegalArgumentException("An area needs to be set up by exactly 2 opposite edges!");
	    return new int[] { corners[1].getBlockX() - corners[0].getBlockX() + 1, corners[1].getBlockY() - corners[0].getBlockY() + 1, corners[1].getBlockZ() - corners[0].getBlockZ() + 1 };
	}
}




