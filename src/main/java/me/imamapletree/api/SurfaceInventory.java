package me.imamapletree.api;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.imamapletree.Skyconomy;

public class SurfaceInventory implements org.bukkit.inventory.Inventory, Cloneable {
	private Inventory binv;
	private String name;
	private Skyconomy skyconomy = Skyconomy.getInstance();
	
	public SurfaceInventory(org.bukkit.inventory.Inventory inv, String name) {
		this.binv = inv;
		this.name = name;
	}
	
	//Custom inventory methods
	public String getName() {
		return this.name;
	}
	
	public SurfaceInventory clone() {
		SurfaceInventory new_inv = skyconomy.createInventory(this.getHolder(), this.getSize(), this.name);
		new_inv.setContents(this.getContents());
		return new_inv;
	}
	
	public Inventory deepClone() {
		SurfaceInventory new_inv = skyconomy.createInventory(this.getHolder(), this.getSize(), this.name);
		new_inv.setContents(this.getContents());
		return new_inv.toBukkit();
	}
	
	public Inventory toBukkit() {
		return this.binv;
	}
	//End of custom methods
	
	@Override
	public HashMap<Integer, ItemStack> addItem(ItemStack... arg0) throws IllegalArgumentException {
		return this.binv.addItem(arg0);
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(Material arg0) throws IllegalArgumentException {
		return this.binv.all(arg0);
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(ItemStack arg0) {
		return this.binv.all(arg0);
	}

	@Override
	public void clear() {
		this.binv.clear();
	}

	@Override
	public void clear(int arg0) {
		this.binv.clear(arg0);
	}

	@Override
	public boolean contains(Material arg0) throws IllegalArgumentException {
		return this.binv.contains(arg0);
	}

	@Override
	public boolean contains(ItemStack arg0) {
		return this.binv.contains(arg0);
	}

	@Override
	public boolean contains(Material arg0, int arg1) throws IllegalArgumentException {
		return this.binv.contains(arg0, arg1);
	}

	@Override
	public boolean contains(ItemStack arg0, int arg1) {
		return this.binv.contains(arg0, arg1);
	}

	@Override
	public boolean containsAtLeast(ItemStack arg0, int arg1) {
		return this.binv.containsAtLeast(arg0, arg1);
	}

	@Override
	public int first(Material arg0) throws IllegalArgumentException {
		return this.binv.first(arg0);
	}

	@Override
	public int first(ItemStack arg0) {
		return this.binv.first(arg0);
	}

	@Override
	public int firstEmpty() {
		return this.binv.firstEmpty();
	}

	@Override
	public ItemStack[] getContents() {
		return this.binv.getContents();
	}

	@Override
	public InventoryHolder getHolder() {
		return this.binv.getHolder();
	}

	@Override
	public ItemStack getItem(int arg0) {
		return this.binv.getItem(arg0);
	}

	@Override
	public Location getLocation() {
		return this.binv.getLocation();
	}

	@Override
	public int getMaxStackSize() {
		return this.binv.getMaxStackSize();
	}

	@Override
	public int getSize() {
		return this.binv.getSize();
	}

	@Override
	public ItemStack[] getStorageContents() {
		return this.binv.getStorageContents();
	}

	@Override
	public InventoryType getType() {
		return this.binv.getType();
	}

	@Override
	public List<HumanEntity> getViewers() {
		return this.binv.getViewers();
	}

	@Override
	public ListIterator<ItemStack> iterator() {
		return this.binv.iterator();
	}

	@Override
	public ListIterator<ItemStack> iterator(int arg0) {
		return this.binv.iterator(arg0);
	}

	@Override
	public void remove(Material arg0) throws IllegalArgumentException {
		this.binv.remove(arg0);
	}

	@Override
	public void remove(ItemStack arg0) {
		this.binv.remove(arg0);
	}

	@Override
	public HashMap<Integer, ItemStack> removeItem(ItemStack... arg0) throws IllegalArgumentException {
		return this.binv.removeItem(arg0);
	}

	@Override
	public void setContents(ItemStack[] arg0) throws IllegalArgumentException {
		this.binv.setContents(arg0);
	}

	@Override
	public void setItem(int arg0, ItemStack arg1) {
		this.binv.setItem(arg0, arg1);
	}

	@Override
	public void setMaxStackSize(int arg0) {
		this.binv.setMaxStackSize(arg0);
	}

	@Override
	public void setStorageContents(ItemStack[] arg0) throws IllegalArgumentException {
		this.binv.setStorageContents(arg0);	
	}

	@Override
	public boolean isEmpty() {
		return this.binv.isEmpty();
	}

}
