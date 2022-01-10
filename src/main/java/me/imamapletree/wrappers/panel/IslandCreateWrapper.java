package me.imamapletree.wrappers.panel;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imamapletree.panels.Icon;

public class IslandCreateWrapper implements ClickCommandWrapper {
	public boolean onClick(Icon icon, Player player) {
		CommandSender sender = player;
		boolean success = Bukkit.dispatchCommand(sender, "island create");
		return success;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
