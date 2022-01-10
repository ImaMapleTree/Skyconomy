package me.imamapletree.api.bungee.stream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.imamapletree.Skyconomy;
import me.imamapletree.tools.SkyLog;

public class ByteSender {
	private final static Skyconomy skyconomy = Skyconomy.getInstance();
	
	public static ByteArrayDataOutput out() {
		return ByteStreams.newDataOutput();
	}
	
	public static void send(ByteArrayDataOutput out) {
		sendPM(Iterables.getFirst(Bukkit.getOnlinePlayers(), null), out);
	}
	
	public static void sendAndAttach(ByteArrayDataOutput out, String string) {
		sendAttachedPM(Iterables.getFirst(Bukkit.getOnlinePlayers(), null), out, string);
	}
	
	public static void sendPM(Player player, ByteArrayDataOutput out) {
		SkyLog.slog("Sending Bungee..");
		player.sendPluginMessage(skyconomy, "BungeeCord", out.toByteArray());
	}
	
	public static void sendAttachedPM(Player player, ByteArrayDataOutput out, String string) {
		player.setMetadata("ByteTag", new FixedMetadataValue(skyconomy, string));
		player.sendPluginMessage(skyconomy, "BungeeCord", out.toByteArray());
	}

	public static Player getRandomPlayer() {
		return Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
	}
}
