package me.imamapletree.events.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.EnchantingTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.persistence.PersistentDataType;

import de.tr7zw.nbtapi.NBTItem;
import me.imamapletree.Skyconomy;
import me.imamapletree.events.custom.CalibrationInputEvent;

public class PreEnchantListener implements Listener {
	private Skyconomy skyconomy;
	private NamespacedKey key;
	
	public PreEnchantListener(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		this.key = new NamespacedKey(this.skyconomy, "CUSTOM_DATA");
	}
	
	@EventHandler
	public void onInput(PrepareItemEnchantEvent event) {
		EnchantingTable ET = (EnchantingTable) event.getEnchantBlock().getState();
		if(ET.getPersistentDataContainer().has(this.key, PersistentDataType.STRING)) {
			NBTItem nbti = new NBTItem(event.getItem());
			if(!nbti.hasKey("dungeonID")) return;
			Bukkit.getPluginManager().callEvent(new CalibrationInputEvent((EnchantingInventory) event.getInventory(), event.getEnchanter(), ET, event.getItem()));
		}
	}
}
