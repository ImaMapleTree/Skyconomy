package me.imamapletree;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;



public class Example implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final Skyconomy plugin;
	@SuppressWarnings("unused")
	private final Logger log = Bukkit.getLogger();
	
	
	public Example(Skyconomy plugin) throws ClassNotFoundException {
		this.plugin = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label2, String[] args) {
		return true;
	}
	
}
