package me.imamapletree.executors;

import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class JoinExecutor implements Runnable {
	private final Skyconomy skyconomy = Skyconomy.getInstance();
	@SuppressWarnings("rawtypes")
	private final ViaAPI via = Via.getAPI();
	private Player player;
	public JoinExecutor(Player player) {
		this.player = player;
	}
	
	@Override
	public void run() {
		int version = via.getPlayerVersion(this.player.getUniqueId());
		if(version < 735) player.sendMessage(ChatColor.RED + "[Warning] " + ChatColor.YELLOW + "You are currently running Minecraft version: " + ChatColor.GOLD + Utility.getVersion(version) + ChatColor.YELLOW + "  For an optimal experience it is highly recommened you update to 1.16 or higher.");
		if (skyconomy.getPlayerManager().getSurfacePlayers().containsKey(this.player.getName())) skyconomy.getPlayerManager().addSession(this.player);
		else skyconomy.getPlayerManager().add(this.player);
	}
}
