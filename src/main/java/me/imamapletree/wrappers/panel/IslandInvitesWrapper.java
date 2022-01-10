package me.imamapletree.wrappers.panel;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.imamapletree.panels.Icon;

public class IslandInvitesWrapper implements ClickCommandWrapper {
	public boolean onClick(Icon icon, Player player) {
		CommandSender sender = player;
		String commandline = "is invites " + icon.getPanel().getHierarchy().getUUID();
		boolean success = Bukkit.dispatchCommand(sender, commandline);
		if(success) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
