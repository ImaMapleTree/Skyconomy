package me.imamapletree.api.bungee.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.bungee.bridge.IslandBridge;
import me.imamapletree.api.instances.Island;

public class SendDelayedInvite implements Runnable {
	private Player player;
	private Island island;
	private String target;
	
	public SendDelayedInvite(Player player, Island island, String target) {
		this.player = player;
		this.island = island;
		this.target = target;
	}
	
	@Override
	public void run() {
		String server = Skyconomy.getInstance().getCachedInput().getServer(target);
		if(server == null) {player.sendMessage(ChatColor.YELLOW + "Player doesn't exist or isn't online. Please make sure you typed everything correctly."); return;}
		String targetUUID = Skyconomy.getInstance().getCachedInput().getPlayerUUID(target);
		if(targetUUID == null) return;
		IslandBridge.sendInvite(player.getUniqueId().toString(), island.getUUID(), targetUUID, server);
	}
}
