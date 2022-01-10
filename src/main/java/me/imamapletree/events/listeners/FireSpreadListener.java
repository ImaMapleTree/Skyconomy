package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

import me.imamapletree.tools.Utility;

public class FireSpreadListener implements Listener{
	public FireSpreadListener() {
	}
	
	@EventHandler
	public void onSpread(BlockIgniteEvent event) {
		if(event.getCause() != IgniteCause.SPREAD) return;
		if(Utility.SimpleGCheck("FireSpread", event.getBlock().getLocation())) return;
		event.setCancelled(true);
	}
}
