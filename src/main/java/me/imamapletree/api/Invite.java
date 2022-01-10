package me.imamapletree.api;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.bungee.bridge.IslandBridge;
import me.imamapletree.api.instances.Island;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;

public class Invite {
	private final Skyconomy skyconomy = Skyconomy.getInstance();
	private final Island island;
	private final String island_owner;
	private final OfflinePlayer inviter;
	private final Player invitee;
	private final long expire_time;
	
	public Invite(Island island, OfflinePlayer player, Player player2) {
		this.island = island;
		this.inviter = player;
		this.island_owner = island.getOwner();
		this.expire_time = Instant.now().getEpochSecond() + 600;
		this.invitee = player2;
	}
	
	public boolean Refresh() {
		if(this.getTimeRemaining() == 0) {
			SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(invitee.getUniqueId()).orElse(null);
			if(skyplayer != null) skyplayer.removeInvite(this);
			return false;
		} return true;
	}
	
	public long getTimeRemaining() {
		long retime = this.expire_time - Instant.now().getEpochSecond();
		if(retime <= 0) {
			return 0L;
		}
		else return retime;
	}
	
	public Island getIsland() {
		return this.island;
	}
	
	public String getIslandOwner() {
		return this.island_owner;
	}
	
	public OfflinePlayer getInviter() {
		return this.inviter;
	}
	
	public Player getInvitee() {
		return this.invitee;
	}
	
	public ItemStack getCorrespondingStack() {
		String apostrophe = "'";
		ItemStack head_stack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta head_meta = (SkullMeta) head_stack.getItemMeta();
		OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(this.island_owner));
		String name = op.getName();
		head_meta.setOwningPlayer(op);
		if(name.charAt(name.length()-1) != 's') apostrophe = apostrophe + "s";
		head_meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + name + apostrophe + " Island");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "----------------");
		lore.add(ChatColor.AQUA + "From: " + this.getInviter().getName());
		lore.add(ChatColor.WHITE + "----------------");
		lore.add(ChatColor.RED + "Expires in: " + Utility.readableTime(getTimeRemaining(), true));
		head_meta.setLore(lore);
		head_stack.setItemMeta(head_meta);
		return head_stack;
	}
	
	public void Accept(Player player) {
		String apostrophe = "'";
		if(!player.equals(this.invitee)) SkyLog.slog(ChatColor.YELLOW + "Strangely the invitee doesn't equal the player casting the accept, we'll ignore this for now.");
		this.island.addMember(player.getUniqueId().toString(), "MEMBER");
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
		if(skyplayer != null) skyplayer.removeInvite(this);
		OfflinePlayer owner = Bukkit.getOfflinePlayer(UUID.fromString(this.island_owner));
		if(!inviter.getUniqueId().toString().equals(island_owner)) {
			if(owner != null && owner.isOnline()) owner.getPlayer().sendMessage(ChatColor.GREEN + this.invitee.getName() +" has accepted an island invite from " + this.inviter.getName());
		} 
		if(inviter.isOnline()) inviter.getPlayer().sendMessage(ChatColor.GREEN + this.invitee.getName() + " has accepted your invite!");
		if(owner.getName().charAt(owner.getName().length()-1) != 's') apostrophe = apostrophe + "s";
		IslandBridge.registerMember(invitee.getUniqueId().toString(), this.island.getUUID(), "MEMBER", invitee.getWorld().getName());
		player.sendMessage(ChatColor.GREEN + "You have joined " + owner.getName() + apostrophe + " island!");
	}
	
	public void Deny(Player player) {
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
		if(skyplayer != null) skyplayer.removeInvite(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (this.getClass() != obj.getClass()) return false;
		Invite compinv = (Invite) obj;
		if (this.island != compinv.island) return false;
		if (this.island_owner != compinv.island_owner) return false;
		if (this.inviter != compinv.inviter) return false;
		if (this.invitee != compinv.invitee) return false;
		return true;
	}
}
