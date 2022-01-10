package me.imamapletree.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.imamapletree.Skyconomy;

public class PlayerLeave implements Listener {
	@SuppressWarnings("unused")
	private Skyconomy plugin;
	public PlayerLeave(Skyconomy plugin) {
		this.plugin = plugin;
		plugin.getSettings();
	}
	
	@EventHandler
	public void onJoin(PlayerQuitEvent event) {
		@SuppressWarnings("unused")
		Player player = event.getPlayer();
	}
	
}