package me.imamapletree.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.Inventory;

import me.imamapletree.tools.Utility;

public class AccessAnimalListener implements Listener{
	
	public AccessAnimalListener() {
	}
	
	@EventHandler
	public void onInteract(InventoryOpenEvent event) {
		Inventory inv = event.getInventory();
		if (!(inv instanceof AbstractHorseInventory)) return;
		if(Utility.SimpleACheck("AccessAnimalInventory", (Player) event.getPlayer(), event.getPlayer().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
