package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import me.imamapletree.tools.Utility;

public class PlayerFlyListener implements Listener{
	public PlayerFlyListener() {
	}
	
	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		if(Utility.SimpleACheck("UseFly", event.getPlayer(), event.getPlayer().getLocation())) return;
		return;
	}
}
