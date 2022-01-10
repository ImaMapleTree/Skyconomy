package me.imamapletree.events.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.imamapletree.tools.Utility;

public class PassiveSpawnListener implements Listener{
	public PassiveSpawnListener() {
	}
	
	@EventHandler
	public void onInteract(EntitySpawnEvent event) {
		if(!(event.getEntity() instanceof Animals)) return;
		if(Utility.SimpleGCheck("PassiveMobSpawn", event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
