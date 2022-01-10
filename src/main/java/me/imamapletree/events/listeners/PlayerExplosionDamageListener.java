package me.imamapletree.events.listeners;

import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.imamapletree.tools.Utility;

public class PlayerExplosionDamageListener implements Listener{
	public PlayerExplosionDamageListener() {
	}
	
	@EventHandler
	public void onInteract(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(!(event.getDamager() instanceof Explosive)) return;
		if(Utility.SimpleGCheck("DamageByExplosion", event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
