package me.imamapletree;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import com.sk89q.worldedit.WorldEditException;

import me.imamapletree.commands.Skydebug;
import me.imamapletree.commands.StructureCommands;
import me.imamapletree.commands.Test;
import me.imamapletree.tools.SkyLog;

public class Admin implements CommandExecutor, Listener{
	@SuppressWarnings("unused")
	private final Skyconomy plugin;
	@SuppressWarnings("unused")
	private final Logger log = Bukkit.getLogger();
	
	public Admin(Skyconomy plugin) {
		this.plugin = plugin;
	}
	
	
	@Override 
	public boolean onCommand(CommandSender sender, Command command, String label2, String[] args) {
		if(command.getName().equalsIgnoreCase("loadstructure")) {
			try {
				StructureCommands.loadSchematic(sender, command, label2, args);
			} catch (IOException e) {
				SkyLog.log(e);
			} catch (WorldEditException e) {
				SkyLog.log(e);
			}
		} else if(command.getName().equalsIgnoreCase("test")) {
			Test.run(sender, command, label2, args);
		} else if(command.getName().equalsIgnoreCase("skydebug")) {
			Skydebug.run(sender, command, label2, args);
			SkyLog.log("Running debug");
		}
		return false;
	}
}
