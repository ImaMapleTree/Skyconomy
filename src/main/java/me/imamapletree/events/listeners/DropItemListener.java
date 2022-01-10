package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import me.imamapletree.tools.Utility;

public class DropItemListener implements Listener{
	public DropItemListener() {
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(Utility.SimpleACheck("AllowItemDrop", event.getPlayer(), event.getPlayer().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
