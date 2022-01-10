package me.imamapletree.api.bungee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.bungee.bridge.MessageBridge;
import me.imamapletree.api.bungee.util.BungeeCheck;
import me.imamapletree.api.channels.Channel;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;

public class GlobalChannel implements Channel {
	private Skyconomy skyconomy;
	private Player player;
	private World world;
	private String ChatChar;
	private boolean bungee_enabled;
	private UUID uuid;
	private List<String> cached_messages = new ArrayList<String>();
	private String prefix = ChatColor.RED + "[G] " + ChatColor.RESET;
	
	public GlobalChannel(Player player) {
		this.skyconomy = Skyconomy.getInstance();
		this.player = player;
		this.world = player.getWorld();
		this.ChatChar = skyconomy.getSettings().ChatCharacter;
		this.bungee_enabled = BungeeCheck.runningBungee();
		this.uuid = UUID.randomUUID();
		if(!skyconomy.getSettings().GlobalChatPrefix) this.prefix = "";
	}
	
	@Override
	public void sendMessage(String display_name, String message) {
		String formatted_message = this.prefix + this.player.getDisplayName() + ChatChar + ChatColor.RESET + " " + message;
		if(!bungee_enabled) for(Player qplayer : getOnlineRecipients()) qplayer.sendMessage(formatted_message);
		else {
			for(Player qplayer : getOnlineRecipients()) qplayer.sendMessage(formatted_message);
			MessageBridge.sendGlobalMessage(formatted_message, this.world.getName());
		}
	}
	
	public void sendMessageWithRecipients(String[] playerlist, String message) {
		for(String player : playerlist) {
			SkyLog.log(player);
			Player real_player = Bukkit.getPlayerExact(player);
			SkyLog.log(real_player);
			if(real_player == null) continue;
			real_player.sendMessage(message);
		}
	}

	@Override
	public List<String> getRecipients() {
		List<String> recipients = new ArrayList<String>();
		SkyLog.log(skyconomy.getPlayerManager().getSessionPlayers());
		for(SkyPlayer player : skyconomy.getPlayerManager().getSessionPlayers()) {
			if(player.hasChannel()) {
				if(player.getChannel().getType() != "GlobalChannel") continue;
			}
			recipients.add(player.getUUID());
		}
		return recipients;
	}

	@Override
	public List<Player> getOnlineRecipients() {
		List<Player> recipients = new ArrayList<Player>();
		for(SkyPlayer player : skyconomy.getPlayerManager().getSessionPlayers()) {
			if(player.hasChannel()) {
				//SkyLog.log(player.getChannel().getType());
				if(!player.getChannel().getType().equals("GlobalChannel")) continue;
			}
			OfflinePlayer op = player.getPlayer();
			//SkyLog.log(op);
			if(op.isOnline()) recipients.add(op.getPlayer());
		}
		return recipients;
	}
	
	@Override
	public String getType() {
		return Utility.type(this);
	}
	
	public World getWorld() {
		return this.world;
	}

	@Override
	public OfflinePlayer getPlayer() {
		return this.player;
	}
	
	public Player getOnlinePlayer() {
		return this.player;
	}
	
	public UUID getUniqueID() {
		return this.uuid;
	}
	
	public String getFirstMessage() {
		if(this.cached_messages.isEmpty()) return null;
		String message = this.cached_messages.get(0);
		return message;
	}
	public void removeFirstMessage() {
		this.cached_messages.remove(0);
	}
}
