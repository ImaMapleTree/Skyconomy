package me.imamapletree.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;

public class Skydebug {
	private static Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	public static boolean run(CommandSender sender, Command command, String label2, String[] args) {
		if (sender instanceof Player) {
			skyconomy.getMiscManager().getPremades().getDebugPanel().openPanel((Player) sender);
		}
		return false;
	}
}
