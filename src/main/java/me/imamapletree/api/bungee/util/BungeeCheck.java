package me.imamapletree.api.bungee.util;

import org.bukkit.Bukkit;
import org.spigotmc.SpigotConfig;

public class BungeeCheck {
	public static boolean runningBungee() {
		boolean bungee = SpigotConfig.bungee;
		boolean onlineMode = Bukkit.getServer().getOnlineMode();
		if(bungee && !onlineMode) return true;
		return false;
	}
}
