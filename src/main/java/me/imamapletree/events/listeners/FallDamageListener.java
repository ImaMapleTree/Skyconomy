package me.imamapletree.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.imamapletree.tools.Utility;

public class FallDamageListener implements Listener{
	public FallDamageListener() {
	}
	
	@EventHandler
	public void onInteract(EntityDamageEvent event) {
		if(event.getCause() != DamageCause.FALL) return;
		if(!(event.getEntity() instanceof Player)) return;
		if(Utility.SimpleGCheck("FallDamage", event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
