package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.imamapletree.Skyconomy;
import me.imamapletree.events.custom.IslandEnterEvent;

public class IslandLeaveListener implements Listener{
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	
	public IslandLeaveListener (Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	@EventHandler
	public void onEvent(IslandEnterEvent event) {
		event.getPlayer().resetPlayerWeather();
		event.getPlayer().resetPlayerTime();
	}
}
