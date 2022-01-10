package me.imamapletree.events.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

import me.imamapletree.tools.Utility;

public class TouchHangingListener implements Listener{
	public TouchHangingListener() {
	}
	
	@EventHandler
	public void onPlace(HangingPlaceEvent event) {
		if(Utility.SimpleACheck("AffectHanging", event.getPlayer(), event.getBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void OnDestroy(HangingBreakByEntityEvent event) {
		if(event.getEntity() instanceof LeashHitch) return;
		Entity entity = event.getRemover();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(Utility.SimpleACheck("AffectHanging", player, event.getEntity().getLocation())) return;
			event.setCancelled(true);
			return;
		}
	}
}
