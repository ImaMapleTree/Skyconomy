package me.imamapletree.events.listeners.addons;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.EnchantingTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;

import de.tr7zw.nbtapi.NBTItem;
import me.imamapletree.Skyconomy;
import me.imamapletree.tools.NMSUtil;
import net.md_5.bungee.api.ChatColor;

public class PlaceDAPListener implements Listener {
	private Skyconomy skyconomy;
	
	public PlaceDAPListener(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	@EventHandler
	public void onInteract(BlockPlaceEvent event) {
		if(event.getBlock().getType() != Material.ENCHANTING_TABLE) return;
		NBTItem nbti = new NBTItem(event.getItemInHand());
		if(!nbti.getString("CUSTOM_DATA").equals("DUNGEONACCESSPOINT")) return;
		EnchantingTable ET = (EnchantingTable) event.getBlock().getState();
		NamespacedKey key = new NamespacedKey(skyconomy, "CUSTOM_DATA");
		ET.getPersistentDataContainer().set(key, PersistentDataType.STRING, "DUNGEONACCESSPOINT");
		ET.update();
		NMSUtil.renameBlock(ET.getBlock(), ChatColor.DARK_RED + "" + ChatColor.BOLD + "Access Point");
		return;
	}
}
