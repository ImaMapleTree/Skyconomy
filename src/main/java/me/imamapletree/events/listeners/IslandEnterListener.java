package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.imamapletree.Skyconomy;
import me.imamapletree.events.custom.IslandEnterEvent;

public class IslandEnterListener implements Listener{
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	
	public IslandEnterListener (Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	@EventHandler
	public void onEvent(IslandEnterEvent event) {
	}
}
