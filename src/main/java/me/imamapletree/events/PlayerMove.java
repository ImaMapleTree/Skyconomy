package me.imamapletree.events;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.events.custom.IslandEnterEvent;
import me.imamapletree.events.custom.IslandLeaveEvent;

public class PlayerMove implements Listener {
	private Skyconomy plugin;
	private Map<String, Island> island_mapping = new HashMap<String, Island>();
	private Map<String, Long> delayed_event = new HashMap<String, Long>();
	
	public PlayerMove(Skyconomy plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void OnMove(PlayerMoveEvent event) {
		String uuid = event.getPlayer().getUniqueId().toString();
		if(event.getTo() == null) {
			if(island_mapping.containsKey(uuid)) island_mapping.remove(uuid);
			if(delayed_event.containsKey(uuid)) delayed_event.remove(uuid);
			return;
		}
		Long delay = delayed_event.get(uuid);
		Long now = Instant.now().getEpochSecond();
		if(delay == null) delay = now;
		if(now < delay) return;
		Island island = plugin.getIslandManager().locateIsland(event.getTo());
		delayed_event.put(uuid, now+2);
		if(island != null) {island.playTime(event.getPlayer()); island.playWeather(event.getPlayer());}
		if(island_mapping.get(uuid) == island) return;
		if(island_mapping.get(uuid) == null) {
			island_mapping.put(uuid, island);
			Bukkit.getPluginManager().callEvent(new IslandEnterEvent(event.getPlayer(), event.getTo(), island));
		} else if(island_mapping.get(uuid) != null) {
			Island prev_island = island_mapping.get(uuid);
			if(island != null) Bukkit.getPluginManager().callEvent(new IslandEnterEvent(event.getPlayer(), event.getTo(), island));
			Bukkit.getPluginManager().callEvent(new IslandLeaveEvent(event.getPlayer(), event.getTo(), prev_island));
			island_mapping.put(uuid, island);
		}
		
	}
}