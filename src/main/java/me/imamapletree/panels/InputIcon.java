package me.imamapletree.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imamapletree.Skyconomy;
import me.imamapletree.executors.WrapperExecutor;
import me.imamapletree.wrappers.delayed.InventoryOpenWrapper;
import me.imamapletree.wrappers.panel.InputCommandWrapper;
import net.wesjd.anvilgui.AnvilGUI;

public class InputIcon implements Icon {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	private ItemStack item;
	private ItemMeta item_meta;
	private InterfacePanel parent_panel;
	private InputCommandWrapper wrapper = null;
	private Map<String, Object> DataContainer = new HashMap<String, Object>();
	private String CString;
	private String extraneous = null;
	private String GUI_name;
	private String input_name;
	private ItemStack input_item;
	private InputCommandWrapper r_wrapper = null;
	
	public InputIcon(InterfacePanel parent, ItemStack item) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	}
	
	public InputIcon(InterfacePanel parent, ItemStack item, String name) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		if(name != null) this.item_meta.setDisplayName(name);
		this.item.setItemMeta(this.item_meta);
	}
	
	public InputIcon(InterfacePanel parent, ItemStack item, String name, String description) {
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
	
	public void setCommand(InputCommandWrapper wrapped) {
		this.wrapper = wrapped;
	}
	
	public void setRightCommand(InputCommandWrapper wrapped) {
		this.r_wrapper = wrapped;
	}
	
	public InputCommandWrapper getCommand() {
		return this.wrapper;
	}
	
	public void setExtraneous(String obj) {
		this.extraneous = obj;
	}
	
	public String getExtraneous() {
		return this.extraneous;
	}
	
	public void openParent(Player player) {
		if(player == null) return;
		this.parent_panel.setPage(0);
		InterfacePanel parent_panel = this.parent_panel.getParent();
		if(parent_panel != null) parent_panel.openSafePanel(player);
	}
	
	public void setGUIname(String name) {
		this.GUI_name = name;
	}
	
	public void setInputName(String name) {
		this.input_name = name;
	}
	
	public void setInputItem(Material mat) {
		this.input_item = new ItemStack(mat);
	}
	
	public void quickSet(String gname, String iname, Material mat) {
		this.GUI_name = gname;
		this.input_name = iname;
		this.input_item = new ItemStack(mat);
	}
	
	public boolean hasRightClick() {
		if(this.r_wrapper == null) return false;
		return true;
	}
	
	public boolean onRightClick(Player player) {
		if(!hasRightClick()) return onClick(player);
		if(this.extraneous == null) return false;
		return this.r_wrapper.onClick(this, player, this.extraneous);
	}
	
	public boolean onClick(Player player2) {
		if(this.wrapper == null) return false;
		new AnvilGUI.Builder().onClose(player -> {
			player2.closeInventory();
			Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, (Runnable)new WrapperExecutor(new InventoryOpenWrapper(), this.parent_panel, player2, null), 1L);
		}).onComplete((player, text) -> {
			if (this.wrapper.onClick(this, player, text)) { //If it returns true go back to previous panel
				this.parent_panel.openPanel(player2, true);
				return AnvilGUI.Response.close();
			}
			return AnvilGUI.Response.text("Invalid input");
		}).text(this.input_name).item(this.input_item).title(this.GUI_name).plugin(skyconomy).open(player2);
		return false;
	}
}
