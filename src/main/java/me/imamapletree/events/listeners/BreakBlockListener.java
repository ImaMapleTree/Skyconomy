package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.imamapletree.tools.Utility;

public class BreakBlockListener implements Listener{
	public BreakBlockListener () {
	}
	
	@EventHandler
	public void onEvent(BlockBreakEvent event) {
		if(Utility.SimpleACheck("BreakBlock", event.getPlayer(), event.getBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
