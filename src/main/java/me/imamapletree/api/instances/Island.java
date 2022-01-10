package me.imamapletree.api.instances;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

import me.imamapletree.Settings;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.ContainerKey;
import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.api.ToggleData;
import me.imamapletree.tools.JsonHelper;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;
import me.imamapletree.wrappers.panel.ToggleFlagWrapper;
import me.imamapletree.wrappers.panel.ToggleGlobalFlagWrapper;
import net.md_5.bungee.api.ChatColor;
import me.imamapletree.extensions.adapters.FlagSerializer;
import me.imamapletree.panels.CommandIcon;
import me.imamapletree.panels.HierarchyIcon;
import me.imamapletree.panels.Icon;
import me.imamapletree.panels.InterfacePanel;
import me.imamapletree.panels.ToggleIcon;

public class Island {
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	private Settings settings = skyconomy.getSettings();
	private Location center;
	private Location centered_block;
	private int BFC = skyconomy.getSettings().BLOCKS_FROM_CENTER;
	private JSONObject JSONData;
	private String uniqueID = UUID.randomUUID().toString();
	private String owner;
	private World world;
	private String server;
	private FlatCoordinate coordinate;
	private Map<String, String> members = new HashMap<String, String>();
	private Map<Flag, List<String>> Flags = new HashMap<Flag, List<String>>();
	private File path;
	private String cache_reason = null;
	private InterfacePanel role_panel;
	private InterfacePanel member_panel;
	private InterfacePanel global_panel;
	private String weather = "clear";
	private Long time = -1L;
	
	public Island(Location location, Player player) { //I'm pretty sure this method is only called when island is created? IDK LOL HAHA
		this.owner = player.getUniqueId().toString();
		this.center = location;
		this.centered_block = Utility.getLocationCenter(location);
		this.members.put(this.owner, "OWNER");
		this.JSONData = skyconomy.getResourceManager().getDefaultIsland();
		this.Flags = skyconomy.getFlagManager().getFlagValues();
		this.coordinate = new FlatCoordinate(location);
		this.JSONData = this.toJSON();
		this.world = player.getWorld();
		this.server = Utility.stripWorldFolder(player.getWorld().getName());
		this.cache_reason = "creation";
		this.role_panel = skyconomy.getMiscManager().getPremades().generateDRP(this.Flags);
		this.role_panel.getDataContainer().put(new ContainerKey("Island", this.uniqueID), this);
		this.member_panel = skyconomy.getMiscManager().getPremades().generateDMP(this.members);
		this.member_panel.getDataContainer().put(new ContainerKey("Island", null), this);
		this.global_panel = updateGlobalPanel();
	}
	
	@SuppressWarnings("unchecked")
	public Island(JSONObject JO) {
		this.owner = (String) JO.get("owner");
		this.center = JsonHelper.jsonifyLocation((JSONArray) JO.get("center"));
		this.centered_block = Utility.getLocationCenter(this.center);
		this.members = (Map<String, String>) JsonHelper.makeMap((JSONObject) JO.get("members"));
		this.Flags = FlagSerializer.StaticDeserialize(JO.get("flags"));
		this.coordinate = new FlatCoordinate(this.center);
		this.JSONData = JO;
		this.world = skyconomy.getServer().getWorld((String) JO.get("world"));
		String pserver = (String) JO.get("server");
		if(pserver == null) pserver = this.world.getName();
		this.server = Utility.stripWorldFolder(pserver);
		this.uniqueID = (String) JO.get("UUID");
		this.BFC = (int) JsonHelper.readAsInt(JO, "blocks_from_center");
		this.role_panel = skyconomy.getMiscManager().getPremades().generateDRP(this.Flags);
		ContainerKey key = new ContainerKey("Island", this.uniqueID);
		this.role_panel.getDataContainer().put(key, this);
		this.member_panel = skyconomy.getMiscManager().getPremades().generateDMP(this.members);
		this.member_panel.getDataContainer().put(new ContainerKey("Island", null), this);
		this.time = (JO.get("time") != null ? (Long) JO.get("time") : -1L);
		this.weather = (JO.get("weather") != null ? (String) JO.get("weather") : "clear");
		this.global_panel = updateGlobalPanel();
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() {
		this.JSONData = JsonHelper.loadJson(this.path);
		this.members = (Map<String, String>) JsonHelper.makeMap((JSONObject) JSONData.get("members"));
		this.Flags = FlagSerializer.StaticDeserialize(JSONData.get("flags"));
	}
	
	public void saveData() {
		if(settings.IntenseDebug) SkyLog.log(this.path);
		this.JSONData = this.toJSON();
		JsonHelper.saveJson(this.path, this.JSONData);
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public String getRole(String uuid) {
		String role = members.get(uuid);
		if(role == null) return "GUEST";
		return role;
	}
	
	public boolean isAllowed(String FlagName, String role) {
		Optional<Flag> flag = skyconomy.getFlagManager().getFlag(FlagName);
		if(!flag.isPresent()) return false;
		List<String> allowed_people = this.Flags.get(flag.get());
		return allowed_people.contains(role);
	}
	
	public String getUUID() {
		return this.uniqueID;
	}
	
	public File getPath() {
		return this.path;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public String getServer() {
		return this.server;
	}
	
	public Location getCenter() {
		return this.center;
	}
	
	public Location getTPBlock() {
		return this.centered_block;
	}
	
	public FlatCoordinate getCoordinate() {
		return this.coordinate;
	}
	
	public void addMember(String uuid, String role) {
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(UUID.fromString(uuid)).orElse(null);
		if(skyplayer == null) {SkyLog.slog(ChatColor.YELLOW + "There has been a critical error - unable to fetch player from manager, aborting add member process."); SkyLog.slog(uuid); return;}
		skyplayer.setMembership(this.uniqueID, role);
		this.members.put(uuid, role);
		skyconomy.getIslandManager().addToCache(this);
		this.cache_reason = "Modified";
		updateMemberPanel();
	}
	
	public void removeMember(String uuid) {
		SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(UUID.fromString(uuid)).orElse(null);
		if(skyplayer == null) {SkyLog.slog(ChatColor.YELLOW + "There has been a critical error - unable to fetch player from manager, aborting add member process."); SkyLog.slog(uuid); return;}
		this.members.remove(uuid);
		skyplayer.removeMembership(this.uniqueID);
		skyconomy.getIslandManager().addToCache(this);
		this.cache_reason = "Modified";
	}
	
	public List<String> getMembers() {
		List<String> all_members = new ArrayList<String>();
		for(Map.Entry<String, String> entry : this.members.entrySet()) all_members.add(entry.getKey());
		return all_members;
	}
	
	public String promote(String uuid) {
		String ret = null;
		if (!members.containsKey(uuid)) return null;
		String role = members.get(uuid);
		if (role.equals("MEMBER")) {
			addMember(uuid, "ADMIN"); 
			ret = "Admin";
			}
		getMemberPanel();
		return ret;
	}
	
	public String demote(String uuid) {
		String ret = null;
		if(!members.containsKey(uuid)) return null;
		String role = members.get(uuid);
		if (role.equals("ADMIN")) {
			addMember(uuid, "MEMBER"); 
			ret = "Member";
			}
		getMemberPanel();
		return ret;
	}
	
	public boolean isHigherRole(String role1, String role2) {
		if (role1.equals(role2)) return false; //Owner & Owner, Admin & Admin, Member & Member, Guest & Guest
		if (role1.equals("OWNER")) return true; //R2 has to be an admin, member, or guest
		if (role2.equals("OWNER")) return false;
		if (role1.equals("ADMIN")) return true; // R2 has to be an member or guest
		if (role2.equals("ADMIN")) return false; //R1 = member, guest, r2 = admin, member, guest
		if (role1.equals("MEMBER")) return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONData.put("UUID", uniqueID);
		JSONData.put("center", JsonHelper.parseLocationNoFail(this.center, this.server));
		JSONData.put("blocks_from_center", BFC);
		if(this.world != null) JSONData.put("world", this.world.getName());
		else JSONData.put("world", this.server);
		JSONData.put("server", this.server);
		JSONData.put("owner", this.owner);
		JSONData.put("members", members);
		JSONData.put("flags", FlagSerializer.StaticSerialize(this.Flags));
		JSONData.put("weather", this.weather);
		JSONData.put("time", this.time);
		return JSONData;
	}
	//Two really bad implementations of the island method please fix this later or else you're a complete crack-head.
	public Island declarePath(File file) {
		this.path = file;
		return this;
	}
	
	public void printFlags() {
		for (Map.Entry<Flag, List<String>> entry : this.Flags.entrySet()) {
		    SkyLog.log(entry.getKey().getName() + " " + entry.getValue());
		}
		SkyLog.log(this.Flags.size());
	}
	
	public void setPath(File file) {
		this.path = file;
	}
	
	public void updateFlags() {
		this.Flags = skyconomy.getFlagManager().getFlagValues();
		skyconomy.getIslandManager().addToCache(this);
		this.cache_reason = "Modification";
	}
	
	public void setCReason(String reason) {
		this.cache_reason = reason;
	}

	public void openFlagPanel(InterfacePanel parent, Flag flag, Player player) {
		ToggleFlagWrapper TFW = new ToggleFlagWrapper();
		CommandIcon owner_icon;
		CommandIcon admin_icon;
		CommandIcon member_icon;
		CommandIcon guest_icon;
		InterfacePanel ip = new InterfacePanel(3, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + flag.getTitle());
		ip.addBorder(Material.BLACK_STAINED_GLASS_PANE);
		ip.addParent(parent);
		ip.getDataContainer().put(new ContainerKey("Island", null), this);
		List<String> roles = this.Flags.get(flag);
		if(roles.contains("OWNER")) owner_icon = new CommandIcon(ip, new ItemStack(Material.GREEN_TERRACOTTA), ChatColor.GOLD + "Owner", "Current Settings:" + "SPACE_SPACE" + ChatColor.GREEN + "" + ChatColor.BOLD + "Allowed");
		else owner_icon = new CommandIcon(ip, new ItemStack(Material.RED_TERRACOTTA), ChatColor.GOLD + "Owner", "Current Settings:" + "SPACE_SPACE" + ChatColor.RED + "" + ChatColor.BOLD + "Disallowed");
		owner_icon.getDataContainer().put("Flag", flag);
		owner_icon.getDataContainer().put("Role", "OWNER");
		owner_icon.setCommand(TFW);
		if(roles.contains("ADMIN")) admin_icon = new CommandIcon(ip, new ItemStack(Material.GREEN_TERRACOTTA), ChatColor.GOLD + "Admins", "Current Settings:" + "SPACE_SPACE" + ChatColor.GREEN + "" + ChatColor.BOLD + "Allowed");
		else admin_icon = new CommandIcon(ip, new ItemStack(Material.RED_TERRACOTTA), ChatColor.GOLD + "Admins", "Current Settings:" + "SPACE_SPACE" + ChatColor.RED + "" + ChatColor.BOLD + "Disallowed");
		admin_icon.getDataContainer().put("Flag", flag);
		admin_icon.getDataContainer().put("Role", "ADMIN");
		admin_icon.setCommand(TFW);
		if(roles.contains("MEMBER")) member_icon = new CommandIcon(ip, new ItemStack(Material.GREEN_TERRACOTTA), ChatColor.GOLD + "Members", "Current Settings:" + "SPACE_SPACE" + ChatColor.GREEN + "" + ChatColor.BOLD + "Allowed");
		else member_icon = new CommandIcon(ip, new ItemStack(Material.RED_TERRACOTTA), ChatColor.GOLD + "Members", "Current Settings:" + "SPACE_SPACE" + ChatColor.RED + "" + ChatColor.BOLD + "Disallowed");
		member_icon.getDataContainer().put("Flag", flag);
		member_icon.getDataContainer().put("Role", "MEMBER");
		member_icon.setCommand(TFW);
		if(roles.contains("GUEST")) guest_icon = new CommandIcon(ip, new ItemStack(Material.GREEN_TERRACOTTA), ChatColor.GOLD + "Guests", "Current Settings:" + "SPACE_SPACE" + ChatColor.GREEN + "" + ChatColor.BOLD + "Allowed");
		else guest_icon = new CommandIcon(ip, new ItemStack(Material.RED_TERRACOTTA), ChatColor.GOLD + "Guests", "Current Settings:" + "SPACE_SPACE" + ChatColor.RED + "" + ChatColor.BOLD + "Disallowed");
		guest_icon.getDataContainer().put("Flag", flag);
		guest_icon.getDataContainer().put("Role", "GUEST");
		guest_icon.setCommand(TFW);
		ip.addIcon(1, 1, owner_icon);
		ip.addIcon(3, 1, admin_icon);
		ip.addIcon(5, 1, member_icon);
		ip.addIcon(7, 1, guest_icon);
		HierarchyIcon back_icon = new HierarchyIcon(ip, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		ip.addIcon(4, 2, back_icon);
		ip.openPanel(player);
	}
	
	@SuppressWarnings("unchecked")
	public boolean toggleMember(InterfacePanel panel, Flag flag, String role) {
		boolean return_value;
		List<String> role_list = this.Flags.get(flag);
		if(role_list == null) return false;
		if(Utility.type(role_list).equals("ArrayList")) {
			JSONArray JA = new JSONArray();
			for(String string : role_list) JA.add(string);
			role_list = JA;
		}
		if(role_list.size() == 3) role_list.add("NONE");
		Icon changed_icon = null;
		if(role.equals("OWNER")) changed_icon = panel.getIcon(ChatColor.GOLD + "Owner");
		if(role.equals("ADMIN")) changed_icon = panel.getIcon(ChatColor.GOLD + "Admins");
		if(role.equals("MEMBER")) changed_icon = panel.getIcon(ChatColor.GOLD + "Members");
		if(role.equals("GUEST")) changed_icon = panel.getIcon(ChatColor.GOLD + "Guests");
		if(role_list.contains(role)) {role_list.set(role_list.indexOf(role), "NONE"); return_value = false;}
		else {
			if(role.equals("OWNER")) role_list.set(0, role);
			if(role.equals("ADMIN")) role_list.set(1, role); 
			if(role.equals("MEMBER")) role_list.set(2, role);
			if(role.equals("GUEST")) role_list.set(3, role);
			return_value = true;
		} List<String> lore = changed_icon.getLore();
		this.Flags.put(flag, role_list);
		String lore2 = ChatColor.stripColor(lore.get(2));
		if(return_value) {lore.set(2, ChatColor.GREEN + lore2.replace("Disallowed", "Allowed")); changed_icon.setItemType(Material.GREEN_TERRACOTTA);}
		else {lore.set(2, ChatColor.RED + lore2.replace("Allowed", "Disallowed")); changed_icon.setItemType(Material.RED_TERRACOTTA);}
		changed_icon.setLore(lore);
		panel.addIcon(changed_icon);
		Icon icon = panel.getParent().getIcon(ChatColor.GOLD + flag.getTitle());
		icon.setLore(skyconomy.getMiscManager().getPremades().permissionsToLore(this.Flags, flag));
		panel.getParent().addIcon(icon);
		skyconomy.getIslandManager().addToCache(this);
		if(this.cache_reason == null) this.cache_reason = "Modification";
		return return_value;
	}
	
	public void flushData() {
		List<String> list = new ArrayList<String>();
		for(Map.Entry<String, String> entry : this.members.entrySet()) {
			list.add(entry.getKey());
		}
		list.forEach((key)->{removeMember(key);});
	}
	
	public String getCReason() {
		return this.cache_reason;
	}
	
	public void setOwner(String uuid) {
		this.owner = uuid;
	}
	
	public List<String> getFlag(String FlagName) {
		Optional<Flag> flag = skyconomy.getFlagManager().getFlag(FlagName);
		if(!flag.isPresent()) return null;
		List<String> allowed_people = this.Flags.get(flag.get());
		return allowed_people;
	}

	public List<Flag> getGlobalFlags() {
		List<Flag> flag_list = new ArrayList<Flag>();
		for(Map.Entry<Flag, List<String>> entry : this.Flags.entrySet()) {
			if(entry.getValue().size() != 1) continue;
			if(entry.getValue().get(0).equals("ENABLED") || entry.getValue().get(0).equals("DISABLED")) flag_list.add(entry.getKey());
		}
		return flag_list;
	}
	
	public List<String> getFlagValue(Flag flag) {
		return this.Flags.get(flag);
	}
	
	public void setFlagValue(Flag flag, List<String> value) {
		this.Flags.put(flag, value);
		skyconomy.getIslandManager().addToCache(this);
		this.cache_reason = "Modification";
	}
	
	@SuppressWarnings("unused")
	private InterfacePanel updateRolePanel() {
		return this.role_panel = skyconomy.getMiscManager().getPremades().generateDRP(this.Flags);
	}
	
	private InterfacePanel updateGlobalPanel() {
		InterfacePanel global_panel = new InterfacePanel(4, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Global Flags");
		global_panel.addParent(skyconomy.getMiscManager().getPremades().getIslandSettingsPanel());
		global_panel.addBorder(Material.BLUE_STAINED_GLASS_PANE);
		HierarchyIcon back_icon = new HierarchyIcon(global_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		global_panel.addIcon(4, 3, back_icon);
		global_panel.getDataContainer().put(new ContainerKey("Island", null), this);
		
		List<Flag> flags = this.getGlobalFlags();
		int x = 1; int y = 1;
		for(String string : skyconomy.getFlagManager().getGlobalOrder()) {
			Flag flag = flags.stream().filter(f -> f.getName().equals(string)).findAny().orElse(null);
			ToggleIcon new_icon = null;
			if(flag == null) continue;
			List<String> value = this.getFlagValue(flag);
			ItemStack icon_item = new ItemStack(flag.getMaterial());
			if(flag.getName() == "PassiveMobSpawn") icon_item = skyconomy.getMiscManager().Villager_Head;
			String title = (String) skyconomy.getFlagManager().getFlagTitles().get(flag.getName());
			ItemMeta IM = icon_item.getItemMeta();
			IM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			IM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			IM.setDisplayName(ChatColor.GOLD + title);
			List<String> lore = new ArrayList<String>();
			String description = (String) skyconomy.getFlagManager().getFlagDescriptions().get(flag.getName());
			if(description.contains("!")) {
				String[] qargs = description.split("!");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + qargs[0]);
				description = qargs[1];
			}
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + description);
			if (value.contains("ENABLED")) lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled");
			else lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Disabled");
			List<String> lore2 = new ArrayList<String>(lore);
			if(value.get(0).equals("ENABLED")) {
				new_icon = new ToggleIcon(global_panel, icon_item, ChatColor.DARK_AQUA + "" + title, new ToggleData(title, lore, "ENABLED"));
				lore2.set(1, ChatColor.RED + "" + ChatColor.BOLD + "Disabled");
				new_icon.addToggle(new ToggleData(title, lore2, "DISABLED"));
			} else {
				new_icon = new ToggleIcon(global_panel, icon_item, ChatColor.DARK_AQUA + "" + title, new ToggleData(title, lore, "DISABLED"));
				lore2.set(1, ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled");
				new_icon.addToggle(new ToggleData(title, lore2, "ENABLED"));
			}
			new_icon.setExtraneous(flag.getName());
			new_icon.setCommand(new ToggleGlobalFlagWrapper());
			global_panel.addIcon(x, y, new_icon);
			if(x >= 7) {x = 0; y += 1;}
			x += 1;
		}
		return global_panel;
	}
	
	public InterfacePanel updateMemberPanel() {
		//this.member_panel.getDataContainer().put(new ContainerKey("Island", null), this);
		return skyconomy.getMiscManager().getPremades().updateDMP(this.member_panel, this.members);
	}
	
	public InterfacePanel getMemberPanel() {
		return this.member_panel;
	}
	
	public InterfacePanel getRolePanel() {
		return this.role_panel;
	}

	public InterfacePanel getGlobalPanel() {
		return this.global_panel;
	}
	
	public void setDefaultWeather(String weather) {
		this.weather = weather;
		skyconomy.getIslandManager().addToCache(this);
		this.cache_reason = "Modification";
	}
	
	public void setDefaultTime(Long time) {
		this.time = time;
		skyconomy.getIslandManager().addToCache(this);
		this.cache_reason = "Modification";
	}
	
	public void playWeather(Player player) {
		if(this.weather.equals("rain")) player.setPlayerWeather(WeatherType.DOWNFALL);
		else if(this.weather.equals("clear")) player.setPlayerWeather(WeatherType.CLEAR);
		else player.resetPlayerWeather();
		
	}
	
	public void playTime(Player player) {
		if(this.time.equals(-1L)) player.resetPlayerTime();
		else player.setPlayerTime(time, false);
	}

	public void mergeData(Island island) {
		this.members = island.members;
		this.member_panel = island.member_panel;
		this.time = island.time;
		this.weather = island.weather;
		this.role_panel = island.role_panel;
		this.Flags = island.Flags;
	}
	
	public CuboidRegion getWERegion() {
		FlatCoordinate c1 = this.coordinate.clone().subtract(250, 250);
		FlatCoordinate c2 = this.coordinate.clone().add(250, 250);
		BlockVector3 b1 = BlockVector3.at(c1.getX(), 0, c1.getZ());
		BlockVector3 b2 = BlockVector3.at(c2.getX(), 255, c2.getZ());
		return new CuboidRegion((com.sk89q.worldedit.world.World) BukkitAdapter.adapt(this.world), b1, b2);
	}
}
