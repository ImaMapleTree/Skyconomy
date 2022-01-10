package me.imamapletree.wrappers.panel;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;
import me.imamapletree.tools.SkyLog;

public class IslandBiomeWrapper implements ClickCommandWrapper {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	public boolean onClick(Icon icon, Player player) {
		CommandSender sender = player;
		Island island = skyconomy.getMiscManager().getIAP(player.getUniqueId().toString());
		if(island == null) return false;
		SkyLog.slog("island setbiome " + icon.getExtraneous() + " " + island.getUUID());
		boolean success = Bukkit.dispatchCommand(sender, "island setbiome " + icon.getExtraneous().replace(" ", "_") + " " + island.getUUID());
		return success;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
