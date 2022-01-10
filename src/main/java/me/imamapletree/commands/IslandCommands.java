package me.imamapletree.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.biome.BiomeType;

import net.md_5.bungee.api.chat.ClickEvent;
import me.imamapletree.Settings;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.ContainerKey;
import me.imamapletree.api.Invite;
import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.api.bungee.bridge.IslandBridge;
import me.imamapletree.api.bungee.bridge.PlayerBridge;
import me.imamapletree.api.bungee.commands.BIslandWarp;
import me.imamapletree.api.bungee.commands.SendDelayedInvite;
import me.imamapletree.api.bungee.util.BungeeCheck;
import me.imamapletree.api.instances.Island;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.intensives.ChangeBiome;
import me.imamapletree.panels.InterfacePanel;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Stopwatch;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class IslandCommands {
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	private static Settings settings = skyconomy.getSettings();
	
	public static boolean create(CommandSender sender, String[] args) {
		//Completely for debug
		if (args.length == 1) args = ArrayUtils.add(args, "1");
		Player player = ((Player) sender).getPlayer();
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
		if (skyplayer == null) {
			sender.sendMessage(ChatColor.RED + "[Error] Player doesn't exist in cache.");
			return false;
		}
		if(!skyplayer.hasIsland(player.getWorld())) {
			SkyLog.log("Creating Island...");
			Island IS = skyconomy.getIslandManager().createIsland(((Player) sender).getPlayer());
			if (IS != null) {
				if(settings.IntenseDebug) SkyLog.log("The UUID of your new island is: " + IS.getUUID());
				skyplayer.addIsland(player.getWorld(), IS);
				skyconomy.getPlayerManager().addToCache(skyplayer);
				IslandBridge.sendIsland(IS, player.getWorld().getName());
				PlayerBridge.registerIsland(player.getUniqueId().toString(), IS.getUUID(), player.getWorld().getName());
				return true;
			}
			sender.sendMessage(ChatColor.RED + "[Error] Island couldn't be create.");
			return false;
		}
		sender.sendMessage(ChatColor.YELLOW + "You already have an island! Use /island delete to delete your existing island.");
		return false;
	}
	
	public static boolean delete(CommandSender sender, String[] args) {
		Player player = ((Player) sender).getPlayer();
		if(args.length == 1) {
			Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			if (island == null) {
				player.sendMessage(ChatColor.RED + "You currently do not own an island.");
				return false;
			} else {
				player.sendMessage((ChatColor.YELLOW + "------------------------------"));
				player.sendMessage(ChatColor.YELLOW + "Are you sure you'd like to delete your island?");
				player.sendMessage(ChatColor.RED + "(This cannot be undone.)");
				player.spigot().sendMessage(new ComponentBuilder("[Yes]").color(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is delete confirm"))
						.append(" | ").bold(true)
						.append("[No]").color(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is delete false")).create());
				player.sendMessage((ChatColor.YELLOW + "------------------------------"));
				return true;
			}
		} else if(args.length == 2) {
			if(args[1].equals("confirm")) {
				player.teleport(WorldCoordinate.createLocation(player.getWorld(), settings.SpawnCoords));
				Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
				SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
				if(skyplayer == null) {
					SkyLog.slog(new Error("Skyplayer doesn't exist!"));
					sender.sendMessage(ChatColor.RED + "An unexpected error has occured. Please contact a staff member for more help.");
					return false;
				}
				SkyLog.log("The UUID of your current Island is: " + island.getUUID());
				boolean success = skyplayer.removeIsland(island.getWorld(), island.getUUID());
				if(!success) {
					SkyLog.slog("Wasn't able to succeeed in removing the island from the player....");
					sender.sendMessage(ChatColor.RED + "An unexpected error has occured. Please contact a staff member for more help.");
					return false;
				}
				skyconomy.getIslandManager().deleteIsland(island);
				player.sendMessage(ChatColor.GREEN  + "Island successfully deleted.");
				
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static boolean add(CommandSender sender, String[] args) {
		Player player = ((Player) sender).getPlayer();
		if (true) {
			Player target = Bukkit.getServer().getPlayer(args[1]);
			Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			SkyLog.log(island);
			island.addMember(target.getUniqueId().toString(), "MEMBER");
			skyconomy.getIslandManager().addToCache(island);
			return true;
		}
		return false;
	}
	
	public static boolean UpdateFlags(CommandSender sender, String[] args) {
		Player player = ((Player) sender).getPlayer();
		Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
		if (island == null) {
			player.sendMessage("nope lol");
			return false;
		} else {
			island.printFlags();
			island.updateFlags();
			island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			island.printFlags();
			player.sendMessage("Success!");
			return true;
		}
	}
	
	public static boolean home(CommandSender sender) {
		Player player = ((Player) sender).getPlayer();
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
		if (skyplayer == null) {
			sender.sendMessage(ChatColor.RED + "[Error] Player doesn't exist in cache.");
			return false;
		}
		if(skyplayer.hasIsland(player.getWorld())) { //In the future check the islands the player is apart of and take them to their highest ranking island.
			Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			if (!island.getServer().equals(Utility.stripWorldFolder(player.getWorld().getName()))) {
				String server = island.getServer();
				if(BungeeCheck.runningBungee()) {BIslandWarp.connect(player, server);
					PlayerBridge.sendTP(player.getUniqueId().toString(), CoordinateParse.toString(new WorldCoordinate(island.getCenter())), server);
				}
				return true;
			}
			if (island != null) {
				player.teleport(island.getCenter());
				return true;
			}
		} //Jumps here
		return false;
	}

	public static boolean invite(CommandSender sender, String[] args) {
		if(args.length != 2) return false; //And tell the player the right syntax
		Player player = ((Player) sender).getPlayer();
		//For now this command only accepts invite from the original owner of the island
		Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
		Player target = Bukkit.getPlayer(args[1]);
		if(target == null) {
			PlayerBridge.askServerQuery(args[1]);
			PlayerBridge.cachePlayerUUID(args[1]);
			Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, new SendDelayedInvite(player, island, args[1]), 3L);
			//sender.sendMessage(ChatColor.YELLOW + "Player doesn't exist or isn't online. Please make sure you typed everything correctly."); 
			return false;}
		Invite cast_invite = new Invite(island, player, target);
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(target.getUniqueId()).orElse(null);
		if(skyplayer == null) return false;
		if(skyplayer.getInvites().contains(cast_invite)) {
			sender.sendMessage(ChatColor.YELLOW + "That player already has a pending invite!");
			return true;
		}
		if(skyplayer.getMemberships().containsKey(cast_invite.getIsland().getUUID())) {
			sender.sendMessage(ChatColor.YELLOW + "That player is already invited to the island!");
			return true;
		}
		skyplayer.addInvite(cast_invite);
		sender.sendMessage("Sent " + ChatColor.GREEN + target.getName() + ChatColor.RESET + " an invite!");
		return true;
	}
	
	public static boolean invites(CommandSender sender, String[] args) {
		String argument;
		SkyPlayer player = skyconomy.getPlayerManager().getSkyPlayer(((Player) sender).getPlayer().getUniqueId()).orElse(null);
		if(player == null) return false;
		InterfacePanel invite_panel = player.getInvitePanel();
		if(args.length == 1) argument = null;
		else argument = args[1];
		ContainerKey key = new ContainerKey("PseudoParent", null);
		invite_panel.getDataContainer().put(key, argument);
		if(player.getPlayer().getPlayer() != null) invite_panel.openPanel((Player) player.getPlayer().getPlayer());
		return true;
	}
	
	public static boolean members(CommandSender sender, String[] args) {
		String islandID = null;
		Island island;
		if(args.length == 2) islandID = args[1];
		Player player = ((Player) sender).getPlayer();
		if(player == null) return false;
		if(islandID == null) island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
		else island = skyconomy.getIslandManager().getIsland(islandID);
		if(island == null) return false;
		island.getMemberPanel().openPanel(player);
		return true;
	}

	public static boolean transfer(CommandSender sender, String[] args) {
		//is transfer UUID
		if(args.length == 2) {
			sender.sendMessage((ChatColor.YELLOW + "------------------------------"));
			sender.sendMessage(ChatColor.YELLOW + "Are you sure you'd like to transfer your island to " + Utility.getOfflineName(args[1]) + "?");
			sender.sendMessage(ChatColor.RED + "(This cannot be undone. Click an option below.)");
			sender.spigot().sendMessage(new ComponentBuilder("[Yes]").color(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is transfer confirm " + args[1])).create());
			sender.sendMessage((ChatColor.YELLOW + "------------------------------"));
		}
		if(args.length == 3) {
			if (Utility.getOfflineName(args[2]) == null) return false;
			Player senderPlayer = ((Player) sender).getPlayer();
			SkyPlayer senderSkyplayer = skyconomy.getPlayerManager().getSkyPlayer(senderPlayer.getUniqueId()).orElse(null);
			if (senderSkyplayer == null) return false;
			if (!senderSkyplayer.hasIsland(senderPlayer.getWorld())) {sender.sendMessage(ChatColor.YELLOW + "You don't own an island to transfer!"); return false;}
			Island island = skyconomy.getIslandManager().getIsland(senderPlayer.getUniqueId().toString(), senderPlayer.getWorld());
			Player player = Bukkit.getPlayer(UUID.fromString(args[2]));
			if (player == null) sender.sendMessage(ChatColor.YELLOW + "Player must be online to complete transfer!");
			SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
			if (skyplayer == null) {sender.sendMessage(ChatColor.RED + "An error has occured."); return false;}
			if(skyplayer.hasIsland(senderPlayer.getPlayer().getWorld())) {sender.sendMessage(ChatColor.YELLOW + skyplayer.getName() + " must delete their island to gain ownership!"); return true;}
			skyplayer.addIsland(senderPlayer.getWorld(), island);
			island.addMember(skyplayer.getUUID(), "OWNER");
			island.addMember(senderSkyplayer.getUUID(), "MEMBER");
			island.setOwner(skyplayer.getUUID());
			senderPlayer.sendMessage("Island has successfully been transfered to " + ChatColor.GREEN + senderSkyplayer.getName());
			player.sendMessage("You have gained ownership of " + ChatColor.GREEN + senderPlayer.getName() + "'s" + ChatColor.RESET + " island.");
			return true;
		}
		return false;
	}
	
	public static boolean promote(CommandSender sender, String[] args) {
		if(args.length == 2) {
			if(!args[1].contains("-")) {
				Player aPlayer = Bukkit.getPlayer(args[1]);
				if(aPlayer == null) {sender.sendMessage(ChatColor.YELLOW + "Player either isn't online or doesn't exist."); return true;}
				args[1] = aPlayer.getUniqueId().toString();
			}
			Player player = ((Player) sender).getPlayer();
			Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			if (island.getOwner().equals(args[1])) return false;
			String role = island.promote(args[1]);
			if(role == null) {sender.sendMessage(ChatColor.YELLOW + "Failed to promote " + Utility.getOfflineName(args[1]) + "."); return true;}
			IslandBridge.addMember(Utility.getOfflineName(args[1]), island.getUUID(), role);
			sender.sendMessage(ChatColor.GREEN + Utility.getOfflineName(args[1]) + ChatColor.RESET + " has been promoted to " + ChatColor.GOLD + role);
			return true;
		}
		else if(args.length == 3) {
			Player player = ((Player) sender).getPlayer();
			Island island = skyconomy.getIslandManager().getIsland(args[2]);
			if (island.getOwner().equals(args[1])) return false;
			String prole = island.getRole(player.getUniqueId().toString());
			if(prole == null) return false;
			if(!island.isAllowed("ChangeRole", prole)) {player.sendMessage(ChatColor.YELLOW + "You do not have permission to do that."); return true;}
			String role = island.promote(args[1]);
			if (!island.isHigherRole(prole, role)) {sender.sendMessage(ChatColor.YELLOW + "Unable to promote members of the same or higher roles."); return true;}
			if(role == null) {sender.sendMessage(ChatColor.YELLOW + "Failed to promote " + Utility.getOfflineName(args[1]) + "."); return true;}
			IslandBridge.addMember(Utility.getOfflineName(args[1]), island.getUUID(), role);
			sender.sendMessage(ChatColor.GREEN + Utility.getOfflineName(args[1]) + ChatColor.RESET + " has been promoted to " + ChatColor.GOLD + role);
			return true;
		}
		return false;
	}
	
	public static boolean demote(CommandSender sender, String[] args) {
		if(args.length == 2) {
			if(!args[1].contains("-")) {
				Player aPlayer = Bukkit.getPlayer(args[1]);
				if(aPlayer == null) {sender.sendMessage(ChatColor.YELLOW + "Player either isn't online or doesn't exist."); return true;}
				args[1] = aPlayer.getUniqueId().toString();
			}
			Player player = ((Player) sender).getPlayer();
			Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			if (island.getOwner().equals(args[1])) return false;
			String role = island.demote(args[1]);
			if(role == null) {sender.sendMessage(ChatColor.YELLOW + "Failed to demote " + Utility.getOfflineName(args[1]) + "."); return true;}
			sender.sendMessage(ChatColor.GREEN + Utility.getOfflineName(args[1]) + ChatColor.RESET + " has been demoted to " + ChatColor.GOLD + role);
			return true;
		}
		else if(args.length == 3) {
			Player player = ((Player) sender).getPlayer();
			Island island = skyconomy.getIslandManager().getIsland(args[2]);
			if (island.getOwner().equals(args[1])) return false;
			String prole = island.getRole(player.getUniqueId().toString());
			if(prole == null) return false;
			if(!island.isAllowed("ChangeRole", prole)) {player.sendMessage(ChatColor.YELLOW + "You do not have permission to do that."); return true;}
			String role = island.demote(args[1]);
			if (!island.isHigherRole(prole, role)) {sender.sendMessage(ChatColor.YELLOW + "Unable to demote members of the same or higher roles."); return true;}
			if(role == null) {sender.sendMessage(ChatColor.YELLOW + "Failed to demote " + Utility.getOfflineName(args[1]) + "."); return true;}
			sender.sendMessage(ChatColor.GREEN + Utility.getOfflineName(args[1]) + ChatColor.RESET + " has been demoted to " + ChatColor.GOLD + role);
			return true;
		}
		return false;
	}
	
	public static boolean kick(CommandSender sender, String[] args) {
		if(args.length == 2) {
			if(!args[1].contains("-")) {
				Player aPlayer = Bukkit.getPlayer(args[1]);
				if(aPlayer == null) {sender.sendMessage(ChatColor.YELLOW + "Player either isn't online or doesn't exist."); return true;}
				args[1] = aPlayer.getUniqueId().toString();
			}
			Player player = ((Player) sender).getPlayer();
			Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			if (island.getOwner().equals(args[1])) return false;
			island.removeMember(args[1]);
			sender.sendMessage(ChatColor.GREEN + Utility.getOfflineName(args[1]) + ChatColor.RESET + " has been kicked.");
		}
		if(args.length == 3) {
			Player player = ((Player) sender).getPlayer();
			Island island = skyconomy.getIslandManager().getIsland(args[2]);
			if (island.getOwner().equals(args[1])) return false;
			String prole = island.getRole(player.getUniqueId().toString());
			if(prole == null) return false;
			if(!island.isAllowed("KickMember", prole)) {player.sendMessage(ChatColor.YELLOW + "You do not have permission to do that."); return true;}
			String role = island.getRole(args[1]);
			if(role == "GUEST") {sender.sendMessage(ChatColor.YELLOW + Utility.getOfflineName(args[1]) + " is not a member of the island!"); return true;}
			if (!island.isHigherRole(prole, role)) {sender.sendMessage(ChatColor.YELLOW + "Unable to kick members of the same or higher roles."); return true;}
			island.removeMember(args[1]);
			sender.sendMessage(ChatColor.GREEN + Utility.getOfflineName(args[1]) + ChatColor.RESET + " has been kicked.");
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean setbiome(CommandSender sender, String[] args) {
		Stopwatch sw = new Stopwatch();
		sw.start();
		Island island = null;
		Player player = ((Player) sender).getPlayer();
		if(args.length == 2) {
			island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
			if (island == null) {
				player.sendMessage(ChatColor.YELLOW + "You do not own an island! Use [/is create] to create one!");
				return false;
			}
		} else if(args.length == 3) {
			island = skyconomy.getIslandManager().getIsland(args[2]);
		}
		if (island == null) return false;
		CuboidRegion CR = island.getWERegion();
		//EditSession ES = WEUtility.createEditSession(island.getWorld());
		//Integer affected_blocks = WEUtility.setBiome(ES, CR, args[1]);
		Bukkit.getScheduler().scheduleAsyncDelayedTask(skyconomy, (Runnable)new ChangeBiome(CR, new BiomeType(Utility.getBiomeID(args[1]))));
		//if(settings.IntenseDebug) SkyLog.log("Changed biome blocks " + String.valueOf(affected_blocks));
		sender.sendMessage(ChatColor.GREEN + "Changing biome... (this may take a minute)");
		return true;
	}
}
