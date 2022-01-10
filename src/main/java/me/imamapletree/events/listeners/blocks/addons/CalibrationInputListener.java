package me.imamapletree.events.listeners.blocks.addons;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.EnchantingTable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.persistence.PersistentDataType;
import de.tr7zw.nbtapi.NBTItem;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Dungeon;
import me.imamapletree.events.custom.CalibrationInputEvent;
import me.imamapletree.managers.EventManager;
import me.imamapletree.tools.NMSUtil;
import me.imamapletree.tools.SkyLog;
import net.md_5.bungee.api.ChatColor;

public class CalibrationInputListener implements Listener{
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	private NamespacedKey key;
	private EventManager manager;
	
	public CalibrationInputListener(Skyconomy skyconomy, EventManager manager) {
		this.skyconomy = skyconomy;
		this.key = new NamespacedKey(skyconomy, "dungeonID");
		this.manager = manager;
	}
	
	@EventHandler
	@SuppressWarnings("unused")
	public void onInteract(CalibrationInputEvent event) {
		SkyLog.slog("Calibration Event");
		if(event.getItem().getType() == Material.AIR) return;
		EnchantingInventory EI = event.getEnchantInventory();
		EI.remove(event.getItem());
		Integer dungeonID;
		Player player = event.getEnchanter();
		EnchantingTable ET = event.getEnchantingTable();
		NBTItem nbti = new NBTItem(event.getItem());
		if(nbti.getInteger("dungeonID") == null) dungeonID = 1;
		else dungeonID = nbti.getInteger("dungeonID");
		ET.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, dungeonID);
		ET.update();
		Dungeon dungeon = skyconomy.getDungeonManager().getDungeon(dungeonID);
		String name = dungeon.getName();
		String rarityPrefix = dungeon.getRarityPrefix();
		NMSUtil.renameBlock(event.getBlock(), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Target:" + " " + rarityPrefix + ChatColor.BOLD + name);
		manager.addCachedObject(player.getUniqueId(), true);
		player.openEnchanting(ET.getLocation(), true);
		//player.openInventory(player.getOpenInventory().getTopInventory());
	}
}
