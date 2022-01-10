package me.imamapletree.events.listeners.blocks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.imamapletree.tools.MatCheck;
import me.imamapletree.tools.Utility;

public class UsePressurePlate implements Listener{
	public UsePressurePlate() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.PHYSICAL) return;
		if (!MatCheck.isPressurePlate(event.getClickedBlock().getType())) return;
		if(Utility.SimpleACheck("UsePressurePlates", event.getPlayer(), event.getClickedBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
