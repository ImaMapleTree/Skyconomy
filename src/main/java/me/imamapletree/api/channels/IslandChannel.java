package me.imamapletree.api.channels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;

public class IslandChannel implements Channel {
	private Island island;
	private OfflinePlayer player;
	private String ChatChar;
	private String prefix = ChatColor.GOLD + "[" + ChatColor.GOLD + "I" + ChatColor.GOLD + "]" + ChatColor.RESET;
	
	public IslandChannel(OfflinePlayer player, Island island) {
		this.player = player;
		this.island = island;
		this.ChatChar = Skyconomy.getInstance().getSettings().ChatCharacter;
	}
	
	public void sendMessage(String display_name, String message) {
		for(Player player : getOnlineRecipients()) player.sendMessage(prefix + " " + ChatColor.AQUA + display_name + ChatChar + ChatColor.RESET + " " + message);
	}
	
	public List<String> getRecipients() {
		List<String> UUIDs = this.island.getMembers();
		return UUIDs;
	}
	
	public List<Player> getOnlineRecipients() {
		List<Player> online_players = new ArrayList<Player>();
		for(String uuid : getRecipients()) {
			OfflinePlayer offplayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
			if(offplayer.getPlayer() != null) online_players.add(offplayer.getPlayer());
		}
		return online_players;
	}

	public String getType() {
		return Utility.type(this);
	}

	public OfflinePlayer getPlayer() {
		return this.player;
	}
	
	public Island getIsland() {
		return this.island;
	}
}
