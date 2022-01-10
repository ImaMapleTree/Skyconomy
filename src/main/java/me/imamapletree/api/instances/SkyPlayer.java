package me.imamapletree.api.instances;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import me.imamapletree.Skyconomy;
import me.imamapletree.WorldLinker;
import me.imamapletree.api.Invite;
import me.imamapletree.api.bungee.bridge.PlayerBridge;
import me.imamapletree.api.bungee.util.BungeeCheck;
import me.imamapletree.api.channels.Channel;
import me.imamapletree.api.channels.ChannelParse;
import me.imamapletree.panels.CommandIcon;
import me.imamapletree.panels.HierarchyIcon;
import me.imamapletree.panels.InterfacePanel;
import me.imamapletree.tools.JsonHelper;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.wrappers.panel.IconAcceptor;
import net.md_5.bungee.api.ChatColor;

public class SkyPlayer {
	private final Skyconomy skyconomy = Skyconomy.getInstance();
	private final WorldLinker wl = WorldLinker.getInstance();
	private Map<String, String> islands = new HashMap<String, String>();
	private Map<String, String> iMemberships = new HashMap<String, String>();
	private JSONObject JPData;
	private final String uniqueID;
	private final UUID actual_uniqueID;
	private final String Username;
	private final OfflinePlayer player;
	private String cache_reason;
	private final List<Invite> invites = new ArrayList<Invite>();
	private InterfacePanel invite_panel;
	private Channel channel;
	
	public SkyPlayer(Player player) {
		this.uniqueID = player.getUniqueId().toString();
		this.actual_uniqueID = player.getUniqueId();
		this.Username = player.getName();
		this.player = player;
		this.invite_panel = skyconomy.getMiscManager().getPremades().getDefaultInvitePanel();
		this.channel = null; //null is the temporary channel for world-based chat, in the future this will be swapped with in-world/in-server chat meaning that when worlds are subworldized chat will work.
		if ((new File(skyconomy.getPlayerManager().getPlayerFolder() + "\\" + this.uniqueID + ".json")).exists()) {
			loadData(); //probably fine now
		} else {
			SkyLog.log((new File(skyconomy.getPlayerManager().getPlayerFolder() + this.uniqueID + ".json")));
			this.cache_reason = "creation";
			createData();
		}
	}
	
	public SkyPlayer(File file) {
		loadData(file);
		this.uniqueID = (String) this.JPData.get("UUID");
		this.actual_uniqueID = UUID.fromString(this.uniqueID);
		this.Username = (String) this.JPData.get("Username");
		this.player = Bukkit.getOfflinePlayer(this.actual_uniqueID);
		this.channel = ChannelParse.fromJSON((JSONObject) this.JPData.get("channel"));
		this.invite_panel = skyconomy.getMiscManager().getPremades().getDefaultInvitePanel();
	}
	
	@SuppressWarnings("unchecked")
	public SkyPlayer(JSONObject JO) {
		this.JPData = JO;
		this.islands = (Map<String, String>) JsonHelper.makeMap((JSONObject) JPData.get("islands"));
		this.iMemberships = (Map<String, String>) JsonHelper.makeMap((JSONObject) JPData.get("iMemberships"));
		this.uniqueID = (String) this.JPData.get("UUID");
		this.actual_uniqueID = UUID.fromString(this.uniqueID);
		this.Username = (String) this.JPData.get("Username");
		this.player = Bukkit.getOfflinePlayer(this.actual_uniqueID);
		this.channel = ChannelParse.fromJSON((JSONObject) this.JPData.get("channel"));
		this.invite_panel = skyconomy.getMiscManager().getPremades().getDefaultInvitePanel();
	}
	
	@SuppressWarnings("unchecked")
	private void loadData() {
		this.JPData = JsonHelper.loadJson(skyconomy.getPlayerManager().getPlayerFolder(), this.uniqueID + ".json");
		this.islands = (Map<String, String>) JsonHelper.makeMap((JSONObject) JPData.get("islands"));
		this.iMemberships = (Map<String, String>) JsonHelper.makeMap((JSONObject) JPData.get("iMemberships"));
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void loadData(File file) {
		this.JPData = JsonHelper.loadJson(file);
		this.islands = (Map<String, String>) JsonHelper.makeMap((JSONObject) JPData.get("islands"));
		this.iMemberships = (Map<String, String>) JsonHelper.makeMap((JSONObject) JPData.get("iMemberships"));
	}
	
	@SuppressWarnings("unchecked")
	public void saveData() {
		this.JPData.put("islands", this.islands);
		this.JPData.put("iMemberships", this.iMemberships);
		this.JPData.put("channel", ChannelParse.toJSON(this.channel));
		JsonHelper.saveJson(skyconomy.getPlayerManager().getPlayerFolder(), this.JPData, this.uniqueID + ".json");
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		this.JPData.put("islands", this.islands);
		this.JPData.put("iMemberships", this.iMemberships);
		this.JPData.put("channel", ChannelParse.toJSON(this.channel));
		return this.JPData;
	}
	
	@SuppressWarnings("unchecked")
	public void createData() {
		this.JPData = skyconomy.getResourceManager().getDefaultPlayer();
		this.JPData.put("UUID", this.uniqueID);
		this.JPData.put("Username", this.Username);
		this.skyconomy.getPlayerManager().addToCache(this);
	}
	
	public OfflinePlayer getPlayer() {
		return this.player;
	}
	
	public String getName() {
		return this.Username;
	}
	
	public UUID getAUUID() {
		return this.actual_uniqueID;
	}
	
	public String getUUID() {
		return this.uniqueID;
	}
	
	public InterfacePanel getInvitePanel() {
		InterfacePanel inv_panel = this.invite_panel;
		if(hasIsland(getPlayer().getPlayer().getWorld())) inv_panel.addParent(skyconomy.getMiscManager().getPremades().getDefaultIslandPanel());
		else inv_panel.addParent(skyconomy.getMiscManager().getPremades().getNoIslandPanel());
		for(int x = 3; x < 6; x++) {
			for(int y = 0; y < 5; y++) {
				invite_panel.removeIcon(x, y);
			}
		}
		int y = 0;
		for(Invite invite : invites) {
			if(y>4) {
				inv_panel = skyconomy.getMiscManager().getPremades().generateDIV();
				this.invite_panel.addPage(inv_panel);
				y = y-4;
			}
			if(!invite.Refresh()) continue;
			CommandIcon head_icon = new CommandIcon(inv_panel, invite.getCorrespondingStack());
			CommandIcon accept_icon = new CommandIcon(inv_panel, new ItemStack(Material.GREEN_STAINED_GLASS_PANE), ChatColor.GREEN + "" + ChatColor.BOLD + "Accept");
			accept_icon.getDataContainer().put("value", true);
			accept_icon.getDataContainer().put("invite", invite);
			accept_icon.setCommand(new IconAcceptor());
			CommandIcon deny_icon = new CommandIcon(inv_panel, new ItemStack(Material.RED_STAINED_GLASS_PANE), ChatColor.RED + "" + ChatColor.BOLD + "Deny");
			deny_icon.getDataContainer().put("value", false);
			deny_icon.getDataContainer().put("invite", invite);
			deny_icon.setCommand(new IconAcceptor());
			inv_panel.addIcon(3, y, head_icon);
			inv_panel.addIcon(4, y, accept_icon);
			inv_panel.addIcon(5, y, deny_icon);
			y += 1;
		}
		HierarchyIcon back_icon = new HierarchyIcon(inv_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		inv_panel.addIcon(1, 3, back_icon);
		return inv_panel;
	}

	public void addIsland(World world, Island island) {
		this.islands.put(wl.getSuperWorld(world.getName()), island.getUUID());
	}
	
	public boolean removeIsland(World world, String islandUUID) {
		String compare_key = this.islands.get(wl.getSuperWorld(world.getName()));
		
		if(!compare_key.equals(islandUUID)) return false;
		this.islands.remove(wl.getSuperWorld(world.getName()));
		skyconomy.getPlayerManager().addToCache(this);
		return true;
	}
	
	public boolean hasIsland(World world) {
		return islands.containsKey(wl.getSuperWorld(world.getName()));
	}

	public void setReason(String reason) {
		this.cache_reason = reason;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
		skyconomy.getPlayerManager().addToCache(this);
		this.cache_reason = "Modified";
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public boolean hasChannel() {
		if (this.channel == null) return false;
		return true;
	}
	
	public void addInvite(Invite invite) {
		this.invites.add(invite);
		if(BungeeCheck.runningBungee()) PlayerBridge.sendPlayer(this, invite.getInvitee().getWorld().getName());
	}
	
	public void removeInvite(Invite invite) {
		if(!invite.getInvitee().isOnline()) return;
		Player p = invite.getInvitee().getPlayer();
		this.invites.remove(this.invites.indexOf(invite));
		this.getInvitePanel().openPanel(p); //Really scuffed probably fix this if buggy
	}
	
	public List<Invite> getInvites() {
		return this.invites;
	}
	
	public Map<String, String> getMemberships() {
		return this.iMemberships;
	}
	
	public void printIslands() {
		for (Map.Entry<String, String> entry : this.islands.entrySet()) {
		    SkyLog.log(entry.getKey() + " " + entry.getValue());
		}
		SkyLog.log(this.islands.size());
	}
	
	public void setMembership(String iUID, String role) {
		this.iMemberships.put(iUID, role);
		skyconomy.getPlayerManager().addToCache(this);
		this.cache_reason = "Modified";
	}
	
	public void removeMembership(String iUID) {
		this.iMemberships.remove(iUID);
		skyconomy.getPlayerManager().addToCache(this);
		this.cache_reason = "Modified";
	}

	public String getCReason() {
		return this.cache_reason;
	}

	@Override
	public int hashCode() {
		int PRIME = 47;
		int result = 1;
		result = PRIME * result + (this.getAUUID() != null ? this.getAUUID().hashCode() : 0);
		result = PRIME * result + (this.getPlayer() != null ? this.getPlayer().hashCode() : 0);
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o.getClass() != this.getClass()) return false;
		if(!(o instanceof SkyPlayer)) return false;
		SkyPlayer op = (SkyPlayer) o;
		if(op.getPlayer() != this.getPlayer()) return false;
		if(op.getUUID() == null) return false;
		if(!op.getUUID().equals(this.getUUID())) return false;
		return true;
	}
}
