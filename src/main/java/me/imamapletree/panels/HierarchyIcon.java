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

import me.imamapletree.Skyconomy;
import me.imamapletree.api.ContainerKey;
import me.imamapletree.tools.SkyLog;

public class HierarchyIcon implements Icon {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy = Skyconomy.getInstance();
	private ItemStack item;
	private ItemMeta item_meta;
	private InterfacePanel parent_panel;
	private String handler;
	private String child_id;
	private Map<String, Object> DataContainer = new HashMap<String, Object>();
	private String CString;
	private String extraneous;
	private boolean right_click = false;
	
	public HierarchyIcon(InterfacePanel parent, ItemStack item) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	}
	
	public HierarchyIcon(InterfacePanel parent, ItemStack item, String name) {
		this.parent_panel = parent;
		this.item = item;
		this.item_meta = item.getItemMeta();
		this.item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		if(name != null) this.item_meta.setDisplayName(name);
		this.item.setItemMeta(this.item_meta);
	}

	public HierarchyIcon(InterfacePanel parent, ItemStack item, String name, String description) {
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
	
	public Map<String, Object> getDataContainer() {
		return this.DataContainer;
	}
	
	public void openChild(Player player) {
		if(player == null) return;
		List<InterfacePanel> panels = this.parent_panel.getChildren();
		if(panels == null) return;
		if(panels.size() == 0) return;
		InterfacePanel child_panel = panels.get(0);
		if(child_panel != null) child_panel.openPanel(player);
	}
	
	public void openChild(Player player, boolean cloned) {
		if(player == null) return;
		List<InterfacePanel> panels = this.parent_panel.getChildren();
		if(panels == null) return;
		if(panels.size() == 0) return;
		InterfacePanel child_panel = panels.get(0);
		if(child_panel != null) child_panel.openPanel(player, cloned);
	}
	
	public void openChild(String UUID, Player player) {
		if(player == null) return;
		if(UUID == null) {openChild(player); return;}
		InterfacePanel child_panel = this.parent_panel.getChild(UUID);
		if(child_panel != null) child_panel.openPanel(player);
	}
	
	public void openChild(String UUID, Player player, boolean cloned) {
		if(player == null) return;
		if(UUID == null) {openChild(player); return;}
		InterfacePanel child_panel = this.parent_panel.getChild(UUID);
		if(child_panel != null) child_panel.openPanel(player, cloned);
	}
	
	public void openParent(Player player) {
		if(player == null) return;
		this.parent_panel.setPage(0);
		InterfacePanel parent_panel = this.parent_panel.getParent();
		if(parent_panel != null) parent_panel.openSafePanel(player);
	}
	
	public void setHandler(String handler) {
		if (handler.equalsIgnoreCase("forward")) this.handler = "forward";
		if (handler.equalsIgnoreCase("cloned_forward")) this.handler = "cforward";
		if (handler.equalsIgnoreCase("back")) this.handler = "back";
		if (handler.equalsIgnoreCase("nextpage")) this.handler = "nextpage";
		if (handler.equalsIgnoreCase("prevpage")) this.handler = "prevpage";
		if (handler.equalsIgnoreCase("pseudoback")) this.handler = "pback";
	}
	
	public void setHandler(String handler, String CUUID) {
		if (handler.equalsIgnoreCase("forward")) this.handler = "forward";
		if (handler.equalsIgnoreCase("cloned_forward")) this.handler = "cforward";
		this.child_id = CUUID;
		if (handler.equalsIgnoreCase("back")) this.handler = "back";
		if (handler.equalsIgnoreCase("hswitch")) {this.handler = "hswitch";}
	}
	
	public void setCoordString(String CString) {
		this.CString = CString;
	}
	
	public String getCoordString() {
		return this.CString;
	}
	
	public void setExtraneous(String string) {
		this.extraneous = string;
	}
	
	public String getExtraneous() {
		return this.extraneous;
	}
	
	public boolean hasRightClick() {
		return this.right_click;
	}
	
	public boolean onRightClick(Player player) {
		if(!hasRightClick()) return onClick(player);
		return true;
	}
	
	public boolean onClick(Player player) {
		if(handler == "forward") openChild(this.child_id, player);
		if(handler == "cforward") openChild(this.child_id, player, true);
		if(handler == "back") openParent(player);
		if(handler == "nextpage") this.parent_panel.nextPage(player);
		if(handler == "prevpage") this.parent_panel.previousPage(player);
		if(handler == "pback") {
			SkyLog.log(this.parent_panel);
			ContainerKey key = new ContainerKey("PseudoParent", null);
			String HID = (String) this.parent_panel.getDataContainer().get(key);
			if(HID == null) return true;
			//skyconomy.getHierarchyManager().getHierarchy(HID).getApex().openPanel(player);
			return false;
		}
		return false;
	}
}
