package me.imamapletree.api;

public class InventoryCoordinate implements Coordinate
{
	protected int X;
	protected int Y;
	
	public InventoryCoordinate(final int X, final int Y) {
		this.X = X;
		this.Y = Y;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getZ() {
		return this.Y;
	}
	
	public int invIndex() {
		int RTI = this.Y * 9;
		return RTI + this.X;
	}
	
	public static InventoryCoordinate SlotToCoord(final int index) {
		double YD  = Math.floor(index/9);
		int Y = (int) YD;
		int X = index % 9;
		return new InventoryCoordinate(X, Y);
	}
	
	@Override
	public String toString() {
		return "InventoryCoordinate [Xs=" + String.valueOf(this.X) + ", Ys=" + String.valueOf(this.Y) +"]";
	}
}
