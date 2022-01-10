package me.imamapletree.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.imamapletree.Skyconomy;
import me.imamapletree.executors.JoinExecutor;

public class PlayerJoin implements Listener {
	private Skyconomy plugin;
	public PlayerJoin(Skyconomy plugin) {
		this.plugin = plugin;
		plugin.getSettings();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, (Runnable)new JoinExecutor(player));
	}
	
}