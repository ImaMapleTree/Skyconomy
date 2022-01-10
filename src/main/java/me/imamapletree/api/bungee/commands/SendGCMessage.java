package me.imamapletree.api.bungee.commands;

import org.bukkit.OfflinePlayer;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.SkyPlayer;

public class SendGCMessage {
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	
	public static void send(String message) {
		for(SkyPlayer player : skyconomy.getPlayerManager().getSessionPlayers()) {
			if(player.hasChannel()) {
				if(!player.getChannel().getType().equals("GlobalChannel")) continue;
			}
			OfflinePlayer op = player.getPlayer();
			if(op.isOnline()) op.getPlayer().sendMessage(message);
		}
	}
}
