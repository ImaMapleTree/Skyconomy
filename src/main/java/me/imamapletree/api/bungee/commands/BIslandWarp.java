package me.imamapletree.api.bungee.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;

import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.api.bungee.stream.ByteSender;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.Utility;

public class BIslandWarp {
	public static void connect(Player player, String server) {
		ByteArrayDataOutput out = ByteSender.out();
		out.writeUTF("Connect");
		out.writeUTF(server);
		ByteSender.sendPM(player, out);
	}
	
	public static void teleport(String uuid, String WCS) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(Utility.hyphenateUUID(uuid)));
		if(!op.isOnline()) return;
		Player player = op.getPlayer();
		player.teleport(WorldCoordinate.createLocation(player.getWorld(), CoordinateParse.WorldCoordParse(WCS)));
	}
}
