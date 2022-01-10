package me.imamapletree.tools;

import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.api.WorldCoordinate;

public class Operator {
	public static boolean isGreater(int n1, int n2) {
		return n1 > n2;
	}
	public static boolean isGreater(double n1, double n2) {
		return n1 > n2;
	}
	public static boolean isGreater(long n1, long n2) {
		return n1 > n2;
	}
	public static boolean isGreater(float n1, float n2) {
		return n1 > n2;
	}
	public static boolean isGreater(FlatCoordinate ic1, FlatCoordinate ic2) {
		return ic1.getX()+ic1.getZ() > ic2.getX()+ic2.getZ();
	}
	public static boolean isGreater(WorldCoordinate wc1, WorldCoordinate wc2) {
		return wc1.getX()+wc1.getY()+wc1.getZ() > wc2.getX()+wc2.getY()+wc2.getZ();
	}
	//
	public static boolean isGreaterX(FlatCoordinate ic1, FlatCoordinate ic2) {
		return ic1.getX() > ic2.getX();
	}
	public static boolean isGreaterZ(FlatCoordinate ic1, FlatCoordinate ic2) {
		return ic1.getZ() > ic2.getZ();
	}
	public static boolean isLessX(FlatCoordinate ic1, FlatCoordinate ic2) {
		return ic1.getX() < ic2.getX();
	}
	public static boolean isLessZ(FlatCoordinate ic1, FlatCoordinate ic2) {
		return ic1.getZ() < ic2.getZ();
	}
	public static boolean isGreaterX(WorldCoordinate wc1, WorldCoordinate wc2) {
		return wc1.getX() > wc2.getX();
	}
	public static boolean isGreaterZ(WorldCoordinate wc1, WorldCoordinate wc2) {
		return wc1.getZ() > wc2.getZ();
	}
	public static boolean isLessX(WorldCoordinate wc1, WorldCoordinate wc2) {
		return wc1.getX() < wc2.getX();
	}
	public static boolean isLessZ(WorldCoordinate wc1, WorldCoordinate wc2) {
		return wc1.getZ() < wc2.getZ();
	}
}
