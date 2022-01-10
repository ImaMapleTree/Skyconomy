package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import me.imamapletree.tools.MatCheck;
import me.imamapletree.tools.Utility;

public class PlaceRedstoneListener implements Listener{
	public PlaceRedstoneListener () {
	}
	
	@EventHandler
	public void onEvent(BlockPlaceEvent event) {
		if(!(MatCheck.isRedstone(event.getBlock().getType()))) return;
		if(Utility.SimpleACheck("PlaceBlock", event.getPlayer(), event.getBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
