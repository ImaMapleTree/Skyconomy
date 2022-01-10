package me.imamapletree.events.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.EnchantingTable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Dungeon;
import me.imamapletree.managers.EventManager;
import me.imamapletree.tools.NMSUtil;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;

public class OpenEnchantingTableListener implements Listener{
	private Skyconomy skyconomy;
	private NamespacedKey key;
	private NamespacedKey destinationKey;
	private EventManager manager;
	
	public OpenEnchantingTableListener(Skyconomy skyconomy, EventManager manager) {
		this.skyconomy = skyconomy;
		this.key = new NamespacedKey(this.skyconomy, "CUSTOM_DATA");
		this.destinationKey = new NamespacedKey(this.skyconomy, "dungeonID");
		this.manager = manager;
	}
	
	@EventHandler
	public void onInteract(InventoryOpenEvent event) {
		if(!(event.getInventory() instanceof EnchantingInventory)) return;
		EnchantingInventory EI = (EnchantingInventory) event.getInventory();
		Block block = EI.getLocation().getBlock();
		EnchantingTable ET = (EnchantingTable) block.getState();
		if(!ET.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
		if(!ET.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals("DUNGEONACCESSPOINT")) return;
		Player player = Utility.adaptPlayer(event.getPlayer());
		Boolean TC = true;
		if(TC.equals(manager.getCachedObject(player.getUniqueId()))) {
			EI.setSecondary(skyconomy.getDungeonManager().COMPASS);
			player.updateInventory();
			manager.removeCachedObject(player.getUniqueId());
			return;
		}
		//EnchantmentInventory EI = new EnchantmentInventory((EnchantingInventory) event.getInventory(), ChatColor.DARK_RED + "" + ChatColor.BOLD + "Access Point");
		
		NMSUtil.renameBlock(block, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Access Point");
		if(ET.getPersistentDataContainer().has(destinationKey, PersistentDataType.INTEGER)) {
			Integer dungeonID = ET.getPersistentDataContainer().get(destinationKey, PersistentDataType.INTEGER);
			Dungeon dungeon = skyconomy.getDungeonManager().getDungeon(dungeonID);
			String name = dungeon.getName();
			String rarityPrefix = dungeon.getRarityPrefix();
			NMSUtil.renameBlock(block, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Target:"  + " " + rarityPrefix + ChatColor.BOLD + name);
			EI.setSecondary(skyconomy.getDungeonManager().COMPASS);
			player.closeInventory();
		}
	}
}
