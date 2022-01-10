package me.imamapletree;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.commands.*;
import me.imamapletree.panels.InterfacePanel;
import me.imamapletree.panels.PremadePanels;
import me.imamapletree.tools.SkyLog;



public class SourceIsland implements CommandExecutor, Listener {
	private final Skyconomy plugin;
	private InterfacePanel nisPanel;
	private InterfacePanel disPanel;
	private PremadePanels premades;
	
	public SourceIsland(Skyconomy plugin) {
		this.plugin = plugin;
		this.premades = plugin.getMiscManager().getPremades();
		
		//Initialize panel
		this.nisPanel = premades.getNoIslandPanel();
		
		this.disPanel = premades.getDefaultIslandPanel();
	}
	
	public boolean island_error(CommandSender sender, Command command, String label2, String[] arg) {
		if (!(sender instanceof Player)) return false;
		Player player = ((Player) sender).getPlayer();
		SkyPlayer skyplayer = plugin.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
		if (skyplayer == null) {
			SkyLog.slog(ChatColor.RED + "[Error] Player doesn't exist in cache.");
			sender.sendMessage(ChatColor.RED + "An unexpected error has occured. Please contact a staff member for more help.");
			this.nisPanel.openPanel(player);
			return true;
		}
		if(skyplayer.hasIsland(player.getWorld())) this.disPanel.openPanel(player);
		else this.nisPanel.openPanel(player);
		return true;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label2, String[] args) {
		Player player = ((Player) sender).getPlayer();
		if (args.length < 1) {
			plugin.getMiscManager().addIAP(player.getUniqueId().toString(), plugin.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld()));
			island_error(sender, command, label2, args);
		} else {
			if (command.getName().equalsIgnoreCase("island") || command.getName().equalsIgnoreCase("is")) {
				String first_arg = args[0].toLowerCase();
				switch (first_arg) {
					case "create":
						return IslandCommands.create(sender, args);
					case "test":
						return Test.run(sender, command, label2, args);
					case "delete":
						return IslandCommands.delete(sender, args);
					case "home":
						return IslandCommands.home(sender);
					case "uf":
						return IslandCommands.UpdateFlags(sender, args);
					case "invites":
						return IslandCommands.invites(sender, args);
					case "invite":
						return IslandCommands.invite(sender, args);
					case "members":
						if(args.length == 2) plugin.getMiscManager().addIAP(player.getUniqueId().toString(), plugin.getIslandManager().getIsland(args[1]));
						return IslandCommands.members(sender, args);
					case "transfer":
						return IslandCommands.transfer(sender, args);
					case "promote":
						if(args.length == 3) plugin.getMiscManager().addIAP(player.getUniqueId().toString(), plugin.getIslandManager().getIsland(args[2]));
						return IslandCommands.promote(sender, args);
					case "demote":
						if(args.length == 3) plugin.getMiscManager().addIAP(player.getUniqueId().toString(), plugin.getIslandManager().getIsland(args[2]));
						return IslandCommands.demote(sender, args);
					case "kick":
						if(args.length == 3) plugin.getMiscManager().addIAP(player.getUniqueId().toString(), plugin.getIslandManager().getIsland(args[2]));
						return IslandCommands.kick(sender, args);
					case "setbiome":
						if(args.length == 3) plugin.getMiscManager().addIAP(player.getUniqueId().toString(), plugin.getIslandManager().getIsland(args[2])); //island setbiome taiga asddsadsa
						return IslandCommands.setbiome(sender, args);
					default:
						return island_error(sender, command, label2, args);
				}
			}
		}
		return false;
	}
	
}
