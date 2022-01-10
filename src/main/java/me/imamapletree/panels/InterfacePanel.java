package me.imamapletree.panels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.ContainerKey;
import me.imamapletree.api.InventoryCoordinate;
import me.imamapletree.api.SimpleHierarchy;
import me.imamapletree.api.SkyconomyInventoryHolder;
import me.imamapletree.api.SurfaceInventory;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.SkyLog;
import net.md_5.bungee.api.ChatColor;

public class InterfacePanel implements Cloneable {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	private String ID;
	private String name;
	private Integer rows;
	private Integer size;
	private SimpleHierarchy hierarchy;
	private Map<String, Icon> IconRegistry = new HashMap<String, Icon>();
	private SurfaceInventory inventory;
	private Map<ContainerKey, Object> data_container = new HashMap<ContainerKey, Object>();
	private Map<Integer, InterfacePanel> pages = new HashMap<Integer, InterfacePanel>();
	private Integer current_page = 0;
	
	public InterfacePanel(int rows, String name) {
		this.ID = UUID.randomUUID().toString();
		this.name = name;
		this.rows = rows;
		this.size = rows*9;
		this.inventory = skyconomy.createInventory((InventoryHolder)new SkyconomyInventoryHolder(this), this.size, this.name);
		this.pages.put(0, this);
		this.hierarchy = new SimpleHierarchy(this);
	}
	
	private InterfacePanel(InterfacePanel panel) {
		this.ID = UUID.randomUUID().toString();
		this.name = panel.name;
		this.rows = panel.rows;
		this.size = panel.size;
		this.inventory = panel.inventory.clone();
		this.pages = new HashMap<Integer, InterfacePanel>();
		this.current_page = panel.current_page;
		this.IconRegistry = panel.IconRegistry;
		this.hierarchy = panel.hierarchy;
		this.pages.put(0, this);
	}
		
	public String getUUID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addIcon(Icon icon) {
		if(icon == null) return;
		this.pages.get(this.current_page).IconRegistry.put(icon.getCoordString(), icon);
		int index = (CoordinateParse.InventoryCoordParse(icon.getCoordString())).invIndex();
		this.pages.get(this.current_page).inventory.setItem(index, icon.getItemStack());
	}
	
	public void addIcon(int x, int y, Icon icon) {
		InventoryCoordinate coord = new InventoryCoordinate(x, y);
		this.pages.get(this.current_page).IconRegistry.put(CoordinateParse.toString(coord), icon);
		icon.setCoordString(CoordinateParse.toString(coord));
		int index = coord.invIndex();
		this.pages.get(this.current_page).inventory.setItem(index, icon.getItemStack());
	}
	
	public void addIcon(String CString, Icon icon) {
		this.pages.get(this.current_page).IconRegistry.put(CString, icon);
		icon.setCoordString(CString);
		int index = CoordinateParse.InventoryCoordParse(CString).invIndex();
		this.pages.get(this.current_page).inventory.setItem(index, icon.getItemStack());
	}
	
	public Icon getIcon(int x, int y) {
		return getIcon(CoordinateParse.toString(new InventoryCoordinate(x, y)));
	}
	
	public Icon getIcon(InventoryCoordinate coord) {
		return this.pages.get(this.current_page).IconRegistry.get(CoordinateParse.toString(coord));
		/*
		if(pages.size() == 0) return this.IconRegistry.get(CoordinateParse.toString(coord));
		for (int i = 0; i < pages.size(); i++) {
			Icon reticon = pages.get(i).IconRegistry.get(CoordinateParse.toString(coord));
			if(reticon != null) return reticon;
		} return null;
		*/
	}
	
	public Icon getIcon(Integer slot) {
		InventoryCoordinate coords = InventoryCoordinate.SlotToCoord(slot);
		return this.pages.get(this.current_page).IconRegistry.get(CoordinateParse.toString(coords));
		/*
		if(pages.size() == 0) return this.IconRegistry.get(CoordinateParse.toString(coords));
		for (int i = 0; i < pages.size(); i++) {
			Icon reticon = pages.get(i).IconRegistry.get(CoordinateParse.toString(coords));
			if(reticon != null) return reticon;
		} return null;
		*/
	}
	
	public Icon getIcon(String name) {
		return this.pages.get(this.current_page).IconRegistry.values().stream().filter(icon -> icon.getItemName().equals(name)).findAny().orElse(null);
		/*
		if(pages.size() == 0) return this.IconRegistry.values().stream().filter(icon -> icon.getItemName().equals(name)).findAny().orElse(null);
		for (int i = 0; i < pages.size(); i++) {
			Icon reticon = pages.get(i).IconRegistry.values().stream().filter(icon -> icon.getItemName().equals(name)).findAny().orElse(null);
			if(reticon != null) return reticon;
		} return null;
		*/
	}
	
	public void removeIcon(int x, int y) {
		InventoryCoordinate coord = new InventoryCoordinate(x, y);
		this.pages.get(this.current_page).IconRegistry.remove(CoordinateParse.toString(coord));
		int index = coord.invIndex();
		this.pages.get(this.current_page).inventory.setItem(index, new ItemStack(Material.AIR));
	}
	
	public void clear() {
		this.inventory.clear();
		this.IconRegistry.clear();
	}
	
	public void addBorder(Material mat1) {
		addBorder(mat1, null);
	}
	
	public void addBorder(Material mat1, Material mat2) {
		if(mat2 == null) mat2 = mat1;
		int alternator = 0;
		for(int x = 0; x < 9; x++) {
			if(this.size % 2 == 1) {
				if(alternator % 2 == 0) this.addIcon(x, 0, new CommandIcon(this, new ItemStack(mat1), " "));
				else this.addIcon(x, 0, new CommandIcon(this, new ItemStack(mat2), " "));
			} else {
				if(alternator % 2 == 0) this.addIcon(x, 0, new CommandIcon(this, new ItemStack(mat2), " "));
				else this.addIcon(x, 0, new CommandIcon(this, new ItemStack(mat1), " "));
			}
			alternator += 1;
		}
		alternator = 0;
		for (int y = 0; y < this.rows-1; y++) {
			if(alternator % 2 == 0) this.addIcon(8, y, new CommandIcon(this, new ItemStack(mat1), " "));
			else this.addIcon(8, y, new CommandIcon(this, new ItemStack(mat2), " "));
			alternator += 1;
		}
		alternator = 0;
		for(int x = 8; x > 0; x--) {
			if(this.size % 2 == 1) {
				if(alternator % 2 == 0) this.addIcon(x, this.rows-1, new CommandIcon(this, new ItemStack(mat1), " "));
				else this.addIcon(x, this.rows-1, new CommandIcon(this, new ItemStack(mat2), " "));
			} else {
				if(alternator % 2 == 0) this.addIcon(x, this.rows-1, new CommandIcon(this, new ItemStack(mat2), " "));
				else this.addIcon(x, this.rows-1, new CommandIcon(this, new ItemStack(mat1), " "));
			}
			alternator += 1;
		}
		alternator = 0;
		for(int y = this.rows-1; y > 0; y--) {
			if(alternator % 2 == 0) this.addIcon(0, y, new CommandIcon(this, new ItemStack(mat1), " "));
			else this.addIcon(0, y, new CommandIcon(this, new ItemStack(mat2), " "));
			alternator += 1;
		}
	}
	
	public void fill(Material mat) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < this.rows; y++) {
				CommandIcon icon = new CommandIcon(this, new ItemStack(mat), " ");
				addIcon(x, y, icon);
			}
		}
	}
	
	public Integer getSize() {
		return this.size;
	}
	
	public void addPage(InterfacePanel new_page) {
		HierarchyIcon prev_icon = new HierarchyIcon(this, new ItemStack(Material.ARROW), ChatColor.AQUA + "Previous Page");
		prev_icon.setHandler("prevpage");
		new_page.addIcon(3, new_page.rows-1, prev_icon);
		
		HierarchyIcon next_icon = new HierarchyIcon(this, new ItemStack(Material.ARROW), ChatColor.AQUA + "Next Page");
		next_icon.setHandler("nextpage");
		(this.pages.get(this.pages.size()-1)).addIcon(5, this.rows-1, next_icon);
		
		new_page.ID = this.ID;
		new_page.pages = this.pages;
		new_page.hierarchy = this.hierarchy;
		new_page.data_container = this.data_container;
		this.pages.put(this.pages.size(), new_page);
	}
	
	public void nextPage(Player player) {
		this.current_page += 1;
		InterfacePanel page = this.pages.get(this.current_page);
		if(page == null) {this.current_page -= 1; return;}
		page.pages = this.pages;
		page.current_page = this.current_page;
		page.openPanel(player);
	}
	
	public void previousPage(Player player) {
		this.current_page -= 1;
		InterfacePanel page = this.pages.get(this.current_page);
		if(page == null) {this.current_page -= 1; return;}
		page.pages = this.pages;
		page.current_page = this.current_page;
		page.openPanel(player);
	}
	
	//Data container
	public Map<ContainerKey, Object> getDataContainer() {
		return this.data_container;
	}

	//Hierachy stuff below
	public void openPanel(Player player) {
		if(player == null) return;
		player.openInventory(this.inventory.toBukkit());
		player.updateInventory();
	}
	
	public void openPanel(Player player, boolean cloned) {
		if(player == null) return;
		if(cloned) player.openInventory(this.inventory.deepClone());
		else player.openInventory(this.inventory.toBukkit());
		player.updateInventory();
	}
	
	public void openSafePanel(Player player) {
		if(player == null) return;
		this.pages.get(current_page).openPanel(player);
	}
	
	public void openSafePanel(Player player, boolean cloned) {
		if(player == null) return;
		this.pages.get(current_page).openPanel(player, cloned);
	}
	
	public void addParent(InterfacePanel IP) {
		this.hierarchy.addParent(IP.getHierarchy());
	}
	
	public void addParent(SimpleHierarchy H) {
		this.hierarchy.addParent(H);
	}
	
	public void addChild(InterfacePanel IP) {
		this.hierarchy.addChild(IP.getHierarchy());
	}
	
	public void addChild(SimpleHierarchy H) {
		this.hierarchy.addChild(H);
	}
	
	public void addChildrenIP(List<InterfacePanel> IPL) {
		for(InterfacePanel IP : IPL) this.hierarchy.addChild(IP.getHierarchy());
	}
	
	public void addChildren(List<SimpleHierarchy> HL) {
		this.hierarchy.addChildren(HL);
	}
	
	public InterfacePanel getParent() {
		return this.hierarchy.getParentAsInterface();
	}
	
	public SimpleHierarchy getParentAsHierarchy() {
		return this.hierarchy.getParent();
	}
	
	public List<InterfacePanel> getChildren() {
		return this.hierarchy.getChildrenAsInterface();
	}
	
	public List<SimpleHierarchy> getChildrenAsHierarchy() {
		return this.hierarchy.getChildren();
	}
	
	public InterfacePanel getChild(String UUID) {
		return getChildren().stream().filter(child -> child.getUUID().equals(UUID)).findAny().orElse(null);
	}
	
	public InterfacePanel getChild(UUID uuid) {
		SimpleHierarchy sh = getChildrenAsHierarchy().stream().filter(child -> child.getUUID().equals(uuid)).findAny().orElse(null);
		if(sh == null) return null;
		return sh.getBound();
	}
	
	public SimpleHierarchy getHiearchyChild(UUID uuid) {
		return getChildrenAsHierarchy().stream().filter(child -> child.getUUID().equals(uuid)).findAny().orElse(null);
	}
	
	public SimpleHierarchy getHierarchy() {
		return this.hierarchy;
	}
	
	public void printIcons() {
		for (Map.Entry<String, Icon> entry : this.IconRegistry.entrySet()) {
		    SkyLog.log(entry.getKey() + " " + entry.getValue().getItemName());
		}
	}

	public void setPage(Integer page) {
		this.current_page = page;
	}
	
	public Inventory getInventory() {
		return this.pages.get(current_page).inventory;
	}
	
	public InterfacePanel clone() {
		return new InterfacePanel(this);
	}
	
	@Override
	public String toString() {
		return "InterfacePanel[" + ChatColor.RESET + "title=" + ChatColor.RESET + name + ChatColor.RESET + ", UUID=" + ID + ", HierarchyUUID=" + (this.hierarchy != null ? this.hierarchy.getUUID() : "null") + ", Size=" +  String.valueOf(size) + "]";
	}
}
	