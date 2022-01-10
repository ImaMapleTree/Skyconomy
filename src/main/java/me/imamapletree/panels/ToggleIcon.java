package me.imamapletree.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imamapletree.api.ToggleData;
import me.imamapletree.tools.ItemUtility;
import me.imamapletree.wrappers.panel.ToggleCommandWrapper;

public class ToggleIcon implements Icon {
	private ItemStack item;
	private ItemStack original_item;
	private ItemMeta item_meta;
	private InterfacePanel parent_panel;
	private ToggleCommandWrapper wrapper = null;
	private Map<String, Object> DataContainer = new HashMap<String, Object>();
	private String CString;
	private String extraneous = null;
	private List<ToggleData> toggle_list = new ArrayList<ToggleData>();
	private Integer current_toggle = 0;
	private boolean right_click = false;
	
	public ToggleIcon(InterfacePanel parent, ItemStack item, ToggleData TD) {
		this.parent_panel = parent;
		this.item = item;
		this.original_item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		this.toggle_list.add(TD);
		initIcon();
	}
	
	public ToggleIcon(InterfacePanel parent, ItemStack item, String name, ToggleData TD) {
		this.parent_panel = parent;
		this.item = item;
		this.original_item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		if(name != null) this.item_meta.setDisplayName(name);
		this.item.setItemMeta(this.item_meta);
		this.toggle_list.add(TD);
		initIcon();
	}
	
	public void setItemName(String name) {
		this.item_meta.setDisplayName(name);
		this.item.setItemMeta(this.item_meta);
	}
	
	public void setLore(List<String> lore) {
		this.item_meta.setLore(lore);
		this.item.setItemMeta(this.item_meta);
	}
	
	public void setItemType(Material mat) {
		this.item.setType(mat);
	}
	
	public void setItemMeta(ItemMeta meta) {
		this.item.setItemMeta(meta);
	}
	
	public void setItemStack(ItemStack item) {
		this.item = item;
	}
	
	public String getItemName() {
		return this.item_meta.getDisplayName();
	}
	
	public List<String> getLore() {
		return this.item_meta.getLore();
	}
	
	public Material getItemType() {
		return this.item.getType();
	}
	
	public ItemMeta getItemMeta() {
		return this.item_meta;
	}
	
	public ItemStack getItemStack() {
		return this.item;
	}
	
	public InterfacePanel getPanel() {
		return this.parent_panel;
	}
	
	public void replaceDataContainer(Map<String, Object> new_container) {
		this.DataContainer = new_container;
	}
	
	public Map<String, Object> getDataContainer() {
		return this.DataContainer;
	}
	
	public void setCoordString(String CString) {
		this.CString = CString;
	}
	
	public String getCoordString() {
		return this.CString;
	}
	
	public void setCommand(ToggleCommandWrapper wrapped) {
		this.wrapper = wrapped;
	}
	
	public ToggleCommandWrapper getCommand() {
		return this.wrapper;
	}
	
	public void setExtraneous(String obj) {
		this.extraneous = obj;
	}
	
	public String getExtraneous() {
		return this.extraneous;
	}
	
	public void addToggle(ToggleData toggle) {
		this.toggle_list.add(toggle);
	}
	
	public void addToggles(List<ToggleData> toggles) {
		this.toggle_list.addAll(toggles);
	}
	
	public void removeToggle(ToggleData toggle) {
		if(!this.toggle_list.contains(toggle)) return;
		this.toggle_list.remove(this.toggle_list.indexOf(toggle));
	}
	
	public ToggleData incrementToggle(boolean update) {
		if(this.toggle_list.isEmpty()) return null;
		if(this.current_toggle+1 >= this.toggle_list.size()) this.current_toggle = 0;
		else this.current_toggle += 1;
		if(update) updateIcon(null);
		return this.toggle_list.get(this.current_toggle);
	}
	
	public ToggleData incrementToggle(Player player, boolean update) {
		if(this.toggle_list.isEmpty()) return null;
		if(this.current_toggle+1 >= this.toggle_list.size()) this.current_toggle = 0;
		else this.current_toggle += 1;
		if(update) updateIcon(player);
		return this.toggle_list.get(this.current_toggle);
	}
	
	public ToggleData decrementToggle(boolean update) {
		if(this.toggle_list.isEmpty()) return null;
		if(this.current_toggle-1 < 0) this.current_toggle = this.toggle_list.size()-1;
		else this.current_toggle -= 1;
		if(update) updateIcon(null);
		return this.toggle_list.get(this.current_toggle);
	}
	
	public boolean setCurrent(ToggleData TD) {
		if(this.toggle_list.isEmpty()) return false;
		if(!this.toggle_list.contains(TD)) return false;
		this.current_toggle = this.toggle_list.indexOf(TD);
		updateIcon(null);
		return true;
	}
	
	public void updateIcon(Player player) {
		//Inventory inv = this.parent_panel.getInventory();
		ItemStack item = this.item;
		ToggleData td = this.toggle_list.get(this.current_toggle);
		List<String> lore = td.getLore();
		ItemUtility.setLore(item, lore);
		if(td.getItemStack() != null) this.item.setType(td.getItemStack().getType());
		else this.item.setType(this.original_item.getType());
		this.parent_panel.addIcon(this);
		if(player != null) {this.parent_panel.openPanel(player, true);}
	}
	
	private void initIcon() {
		ItemStack item = this.item;
		ToggleData td = this.toggle_list.get(this.current_toggle);
		List<String> lore = td.getLore();
		ItemUtility.setLore(item, lore);
	}
	
	public boolean hasRightClick() {
		return this.right_click;
	}
	
	public boolean onRightClick(Player player) {
		if(!hasRightClick()) return onClick(player);
		return true;
	}
	
	public boolean onClick(Player player) {
		if(this.wrapper == null) return false;
		if(this.toggle_list.isEmpty()) return false;
		ToggleData td = incrementToggle(player, true);
		if(td == null) return false;
		boolean success = this.wrapper.onClick(this, player, td.getObject());
		if(success) return false;
		return true;
	}
}
