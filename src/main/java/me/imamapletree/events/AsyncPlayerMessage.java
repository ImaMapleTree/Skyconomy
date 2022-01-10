package me.imamapletree.events;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.SkyPlayer;

public class AsyncPlayerMessage implements Listener {
	private Skyconomy skyconomy;
	
	public AsyncPlayerMessage(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent event) {
		SkyPlayer player = skyconomy.getPlayerManager().getSkyPlayer(event.getPlayer().getUniqueId()).orElse(null);
		if(player == null) return;
		if(!player.hasChannel()) return;
		player.getChannel().sendMessage(event.getPlayer().getName(), event.getMessage());
		event.setCancelled(true);
	}
}