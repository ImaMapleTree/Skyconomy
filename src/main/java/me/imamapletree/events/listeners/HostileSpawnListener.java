package me.imamapletree.events.listeners;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.imamapletree.tools.Utility;

public class HostileSpawnListener implements Listener{
	public HostileSpawnListener() {
	}
	
	@EventHandler
	public void onInteract(EntitySpawnEvent event) {
		if(!(event.getEntity() instanceof Monster)) return;
		if(Utility.SimpleGCheck("HostileMobSpawn", event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
