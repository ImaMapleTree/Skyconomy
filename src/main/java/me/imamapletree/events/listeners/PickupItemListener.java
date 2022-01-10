package me.imamapletree.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import me.imamapletree.tools.Utility;

public class PickupItemListener implements Listener{
	public PickupItemListener() {
	}
	
	@EventHandler
	public void onPickup(EntityPickupItemEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(Utility.SimpleACheck("AllowItemPickup", (Player) event.getEntity(), event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
