package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.imamapletree.tools.Utility;

public class PlaceBlockListener implements Listener{
	public PlaceBlockListener () {
	}
	
	@EventHandler
	public void onEvent(BlockPlaceEvent event) {
		if(Utility.SimpleACheck("PlaceBlock", event.getPlayer(), event.getBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
