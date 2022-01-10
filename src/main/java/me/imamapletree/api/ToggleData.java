package me.imamapletree.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class ToggleData {
	private String sname;
	private List<String> lore;
	private Object obj;
	private ItemStack item;
	
	public ToggleData(String sname, String desc, Object obj) {
		this.sname = sname;
		this.lore = new ArrayList<String>();
		this.lore.add(desc);
		this.obj = obj;
	}
	
	public ToggleData(String sname, List<String> lore, Object obj) {
		this.sname = sname;
		this.lore = lore;
		this.obj = obj;
	}
	
	public ToggleData(String sname, String desc, Object obj, ItemStack item) {
		this.sname = sname;
		this.lore = new ArrayList<String>();
		this.lore.add(desc);
		this.obj = obj;
		this.item = item;
	}
	
	public String getSurfaceName() {
		return this.sname;
	}
	
	public List<String> getLore() {
		return this.lore;
	}
	
	public Object getObject() {
		return this.obj;
	}
	
	public ItemStack getItemStack() {
		return this.item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj.getClass() != this.getClass()) return false;
		ToggleData TD = (ToggleData) obj;
		if(TD.sname != this.sname) return false;
		if(TD.lore != this.lore) return false;
		if(TD.obj != this.obj) return false;
		return true;
	}
}
