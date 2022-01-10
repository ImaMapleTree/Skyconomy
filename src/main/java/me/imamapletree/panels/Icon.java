package me.imamapletree.panels;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Icon {
	public static final ItemStack item = new ItemStack(Material.AIR);
	public static final ItemMeta item_meta = null;
	public static final InterfacePanel parent_panel = null;
	public static final Map<String, Object> DataContainer = null;
	public static final String CString = "";
	
	public boolean onClick(Player player);
	
	public boolean onRightClick(Player player);
	
	public void setItemName(String name);
	
	public void setLore(List<String> lore);
	
	public void setItemType(Material mat);
	
	public void setItemMeta(ItemMeta meta);
	
	public void setItemStack(ItemStack item);
	
	public String getItemName();
	
	public List<String> getLore();
	
	public Material getItemType();
	
	public ItemMeta getItemMeta();
	
	public ItemStack getItemStack();
	
	public InterfacePanel getPanel();
	
	public Map<String, Object> getDataContainer();

	public void setCoordString(String CString);
	
	public String getCoordString();
	
	public void setExtraneous(String string);
	
	public String getExtraneous();

	public boolean hasRightClick();
}
