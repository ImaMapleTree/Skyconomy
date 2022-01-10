package me.imamapletree.events.listeners.blocks;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.imamapletree.tools.Utility;

public class UseBrewingStand implements Listener{
	public UseBrewingStand() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock().getType() != Material.BREWING_STAND) return;
		if(Utility.SimpleACheck("UseBrewingStand", event.getPlayer(), event.getClickedBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
