package me.imamapletree.api.channels;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

import me.imamapletree.Skyconomy;
import me.imamapletree.tools.SkyLog;

public class ChannelParse {
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	
	@SuppressWarnings("unchecked")
	public static JSONObject toJSON(Channel channel) {
		JSONObject json = new JSONObject();
		if (channel == null) return json;
		json.put("type", channel.getType());
		json.put("player", channel.getPlayer().getUniqueId().toString());
		if(channel instanceof IslandChannel) json.put("island", ((IslandChannel) channel).getIsland().getUUID());
		return json;
	}
	
	public static Channel fromJSON(JSONObject json) {
		if(json == null) return null;
		if(json.size() == 0) return null;
		OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString((String) json.get("player")));
		if(json.get("type").equals("IslandChannel")) return new IslandChannel(player, skyconomy.getIslandManager().getIsland((String) json.get("island")));
		SkyLog.log(json.get("type"));
		return null;
	}
}
