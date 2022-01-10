package me.imamapletree.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imamapletree.wrappers.panel.ClickCommandWrapper;

public class CommandIcon implements Icon {
	private ItemStack item;
	private ItemMeta item_meta;
	private InterfacePanel parent_panel;
	private ClickCommandWrapper wrapper = null;
	private ClickCommandWrapper r_wrapper = null;
	private Map<String, Object> DataContainer = new HashMap<String, Object>();
	private String CString;
	private String extraneous = null;
	
	public CommandIcon(InterfacePanel parent, ItemStack item) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	}
	
	public CommandIcon(InterfacePanel parent, ItemStack item, String name) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		if(name != null) this.item_meta.setDisplayName(name);
		this.item.setItemMeta(this.item_meta);
	}
	
	public CommandIcon(InterfacePanel parent, ItemStack item, String name, String description) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		if(name != null) this.item_meta.setDisplayName(name);
		if(description != null) {
			List<String> lore = new ArrayList<String>();
			lore.add(" ");
			if(description.contains("SPACE_SPACE")) {
				String[] qargs = description.split("SPACE_SPACE");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + qargs[0]);
				description = qargs[1];
			}
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + description);
			this.item_meta.setLore(lore);
		}
		this.item.setItemMeta(this.item_meta);
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
	
	public void setCommand(ClickCommandWrapper wrapped) {
		this.wrapper = wrapped;
	}
	
	public void setRightClickCommand(ClickCommandWrapper wrapped) {
		this.r_wrapper = wrapped;
	}
	
	public ClickCommandWrapper getCommand() {
		return this.wrapper;
	}
	
	public void setExtraneous(String obj) {
		this.extraneous = obj;
	}
	
	public String getExtraneous() {
		return this.extraneous;
	}
	
	public boolean hasRightClick() {
		if(this.r_wrapper == null) return false;
		return true;
	}
	
	public boolean onClick(Player player) {
		if(this.wrapper == null) return false;
		return this.wrapper.onClick(this, player);
	}
	
	public boolean onRightClick(Player player) {
		if(this.r_wrapper == null) return false;
		return this.r_wrapper.onClick(this, player);
	}
}
