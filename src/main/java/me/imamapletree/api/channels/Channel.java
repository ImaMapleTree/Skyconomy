package me.imamapletree.api.channels;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface Channel {
	public void sendMessage(String display_name, String message);
	public List<String> getRecipients();
	public List<Player> getOnlineRecipients();
	public String getType();
	public OfflinePlayer getPlayer();
}
