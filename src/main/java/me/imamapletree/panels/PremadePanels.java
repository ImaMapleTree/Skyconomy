package me.imamapletree.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.ToggleData;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.managers.MiscManager;
import me.imamapletree.tools.ItemUtility;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;
import me.imamapletree.wrappers.GlobalFlagWrapper;
import me.imamapletree.wrappers.MPWrapper;
import me.imamapletree.wrappers.panel.*;
import net.md_5.bungee.api.ChatColor;

public class PremadePanels {
	private final Skyconomy skyconomy;
	private final MiscManager miscmanager;
	
	//Wrappers
	private final IslandHomeWrapper IHW = new IslandHomeWrapper();
	private final IslandCreateWrapper ICW = new IslandCreateWrapper();
	private final IslandInvitesWrapper IIW = new IslandInvitesWrapper();
	private final IslandBiomeWrapper IBW = new IslandBiomeWrapper();
	private final EditFlagWrapper EFW = new EditFlagWrapper();
	private Map<Flag, List<String>> flags = new HashMap<Flag, List<String>>();
	
	//Panels
	private InterfacePanel baseDebug;
	private InterfacePanel baseNIP;
	private InterfacePanel baseDIP;
	private InterfacePanel baseISP;
	private InterfacePanel baseDEP;
	private InterfacePanel baseBP;
	
	private InterfacePanel debugPanel;
	private InterfacePanel NIP;
	private InterfacePanel ISP;
	private InterfacePanel DRP;
	private InterfacePanel DIP;
	private InterfacePanel DIV;
	private InterfacePanel DMP;
	private InterfacePanel DEP;
	@SuppressWarnings("unused")
	private InterfacePanel BP;
	
	public PremadePanels(Skyconomy skyconomy, MiscManager miscmanager) {
		this.skyconomy = skyconomy;
		this.flags = miscmanager.getFlagManger().getFlagValues();
		SkyLog.log(miscmanager);
		this.miscmanager = miscmanager;
		//base panels
		this.baseDebug = new InterfacePanel(5, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Debug " + ChatColor.MAGIC + "YAY");
		this.baseNIP =  new InterfacePanel(5, ChatColor.BLUE + "" + ChatColor.BOLD + "Island Menu");
		this.baseDIP = new InterfacePanel(6, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Island Control Panel");
		this.baseISP = new InterfacePanel(5, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Island Settings");
		this.baseDEP = new InterfacePanel(5, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Environment Settings");
		this.baseBP = new InterfacePanel(5, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Island Biome");
		
		//Initalize panels
		this.debugPanel = generateDebug(baseDebug);
		this.NIP = generateNIP(baseNIP);
		this.DIP = generateDIP(baseDIP);
		this.ISP = generateISP(baseISP);
		
		this.DRP = generateDRP(this.flags);
		this.DEP = generateDEP(baseDEP);
		
		this.DIV = generateDIV();
		
		this.BP = generateBPanel(baseBP);
		
		
		
	}
	
	private InterfacePanel generateDebug(InterfacePanel base) {
		base.addBorder(Material.PURPLE_STAINED_GLASS_PANE);
		
		CommandIcon giv1_icon = new CommandIcon(base, new ItemStack(Material.DIAMOND), ChatColor.GREEN + "" + ChatColor.BOLD + "Plains of Beginning");
		giv1_icon.setExtraneous("giv-1");
		giv1_icon.setCommand(new DebugWrapper());
		base.addIcon(1, 1, giv1_icon);
		
		CommandIcon giv2_icon = new CommandIcon(base, new ItemStack(Material.EMERALD), ChatColor.GREEN + "" + ChatColor.BOLD + "Dawning Horizon");
		giv2_icon.setExtraneous("giv-2");
		giv2_icon.setCommand(new DebugWrapper());
		base.addIcon(2, 1, giv2_icon);
		
		CommandIcon chat1_icon = new CommandIcon(base, new ItemStack(Material.BRICK), ChatColor.GREEN + "" + ChatColor.BOLD + "Switch to Island");
		chat1_icon.setExtraneous("s-i");
		chat1_icon.setCommand(new DebugWrapper());
		base.addIcon(3, 1, chat1_icon);
		return base;
	}
	
	private InterfacePanel generateNIP(InterfacePanel base) {
		InterfacePanel panel = base;
		panel.addBorder(Material.BLACK_STAINED_GLASS_PANE);
		
		Icon warp_icon = new CommandIcon(panel, new ItemStack(Material.OAK_SIGN), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Warps");
		panel.addIcon(2, 1, warp_icon);
		
		Icon ach_icon = new CommandIcon(panel, new ItemStack(Material.NETHER_STAR), ChatColor.GREEN + "" + ChatColor.BOLD + "Achievements");
		panel.addIcon(6, 1, ach_icon);
		
		Icon pis_icon = new CommandIcon(panel, new ItemStack(Material.BOOK), ChatColor.GREEN + "" + ChatColor.BOLD + "Your Islands");
		panel.addIcon(1, 2, pis_icon);
		
		CommandIcon create_icon = new CommandIcon(panel, new ItemStack(Material.END_CRYSTAL), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Create Island");
		create_icon.setCommand(this.ICW);
		panel.addIcon(4, 2, create_icon);
		
		Icon top_icon = new CommandIcon(panel, new ItemStack(Material.DIAMOND), ChatColor.GREEN + "" + ChatColor.BOLD + "Top Islands");
		panel.addIcon(7, 2, top_icon);
		
		CommandIcon invite_icon = new CommandIcon(panel, new ItemStack(Material.PAPER), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Invites");
		invite_icon.setCommand(this.IIW);
		panel.addIcon(2, 3, invite_icon);
		
		Icon settings_icon = new CommandIcon(panel, new ItemStack(Material.WRITABLE_BOOK), ChatColor.GREEN + "" + ChatColor.BOLD + "Privacy Settings");
		panel.addIcon(6, 3, settings_icon);
		return panel;
	}
	
	private InterfacePanel generateDIP(InterfacePanel base) {
		ItemStack Ghead = getHead(ChatColor.GREEN + "" + ChatColor.BOLD + "Island Settings", "fe02ba7c-6fb4-458d-af7d-85a72a", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ5YzYzYmM1MDg3MjMzMjhhMTllNTk3ZjQwODYyZDI3YWQ1YzFkNTQ1NjYzYWMyNDQ2NjU4MmY1NjhkOSJ9fX0=");
		InterfacePanel panel = base;
		panel.addBorder(Material.LIME_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE);
		
		CommandIcon invite_icon = new CommandIcon(panel, new ItemStack(Material.PAPER), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Invites");
		invite_icon.setCommand(this.IIW);
		panel.addIcon(1, 1, invite_icon);
		
		CommandIcon member_icon = new CommandIcon(panel, new ItemStack(Material.PLAYER_HEAD), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Members");
		member_icon.setCommand(new IslandMemberWrapper());
		panel.addIcon(4, 1, member_icon);
		
		Icon top_icon = new CommandIcon(panel, new ItemStack(Material.DIAMOND), ChatColor.GREEN + "" + ChatColor.BOLD + "Top Islands");
		panel.addIcon(7, 1, top_icon);
		
		Icon settings_icon = new CommandIcon(panel, new ItemStack(Material.WRITABLE_BOOK), ChatColor.GREEN + "" + ChatColor.BOLD + "Privacy Settings");
		panel.addIcon(3, 2, settings_icon);
		
		HierarchyIcon isett_icon = new HierarchyIcon(panel, Ghead, ChatColor.GREEN + "" + ChatColor.BOLD + "Island Settings");
		panel.addIcon(4, 2, isett_icon);
		
		
		panel.addChild(this.baseISP);
		isett_icon.setHandler("forward", this.baseISP.getUUID());
		
		Icon ach_icon = new CommandIcon(panel, new ItemStack(Material.NETHER_STAR), ChatColor.GREEN + "" + ChatColor.BOLD + "Achievements");
		panel.addIcon(5, 2, ach_icon);
		
		Icon up_icon = new CommandIcon(panel, new ItemStack(Material.GRINDSTONE), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Upgrades");
		panel.addIcon(3, 3, up_icon);
		
		CommandIcon home_icon = new CommandIcon(panel, new ItemStack(Material.RED_BED), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Home");
		home_icon.setCommand(IHW);
		panel.addIcon(4, 3, home_icon);
		
		Icon lock_icon = new CommandIcon(panel, new ItemStack(Material.IRON_BARS), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Lock");
		panel.addIcon(5, 3, lock_icon);
		
		Icon pis_icon = new CommandIcon(panel, new ItemStack(Material.BOOK), ChatColor.GREEN + "" + ChatColor.BOLD + "Your Islands");
		panel.addIcon(1, 4, pis_icon);
		
		Icon warp_icon = new CommandIcon(panel, new ItemStack(Material.OAK_SIGN), ChatColor.GREEN + "" + ChatColor.BOLD + "Island Warps");
		panel.addIcon(7, 4, warp_icon);
		
		return panel;
	}
	
	private InterfacePanel generateISP(InterfacePanel base) {
		ItemStack Ghead = getHead(ChatColor.GREEN + "" + ChatColor.BOLD + "Global Flags", "0ceac85e-159d-4f9d-a1c2-c8acde792f23", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFkZDRmZTRhNDI5YWJkNjY1ZGZkYjNlMjEzMjFkNmVmYTZhNmI1ZTdiOTU2ZGI5YzVkNTljOWVmYWIyNSJ9fX0=");
		InterfacePanel panel = base;
		panel.addBorder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE);
		
		CommandIcon gFlag_icon = new CommandIcon(panel, Ghead, ChatColor.GREEN + "" + ChatColor.BOLD + "Global Flags");
		gFlag_icon.setCommand(new GlobalFlagWrapper());
		panel.addIcon(2, 2, gFlag_icon);
		
		CommandIcon rFlag_icon = new CommandIcon(panel, new ItemStack(Material.CARTOGRAPHY_TABLE), ChatColor.GREEN + "" + ChatColor.BOLD + "Role Flags");
		rFlag_icon.setCommand(new IslandRoleWrapper());
		panel.addIcon(4, 2, rFlag_icon);
		
		HierarchyIcon environment_icon = new HierarchyIcon(panel, new ItemStack(Material.LILY_PAD), ChatColor.GREEN + "" + ChatColor.BOLD + "Environment Settings");
		panel.addIcon(6, 2, environment_icon);
		panel.addChild(this.baseDEP);
		environment_icon.setHandler("forward", this.baseDEP.getUUID());
		
		HierarchyIcon back_icon = new HierarchyIcon(panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		panel.addIcon(4, 4, back_icon);
		
		return panel;
	}
	
	public InterfacePanel generateDRP(Map<Flag, List<String>> gflags) {
		InterfacePanel role_panel = new InterfacePanel(6, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Role Settings");
		role_panel.addBorder(Material.CYAN_STAINED_GLASS_PANE);
		role_panel.addParent(this.baseISP);
		
		InterfacePanel role_panel2 = new InterfacePanel(6, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Role Settings");
		role_panel2.addBorder(Material.CYAN_STAINED_GLASS_PANE);
		
		int x = 1;
		int y = 1;
		for (String flagname : miscmanager.getFlagManger().getFlagOrder()) {
			Flag flag = skyconomy.getFlagManager().getFlag(flagname).orElse(null);
			if(flag == null) continue;
			ItemStack icon_item = new ItemStack(flag.getMaterial());
			ItemMeta IM = icon_item.getItemMeta();
			IM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			IM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			IM.setDisplayName(ChatColor.GOLD + (String) skyconomy.getFlagManager().getFlagTitles().get(flag.getName()));
			List<String> lore = permissionsToLore(gflags, flag);
			IM.setLore(lore);
			icon_item.setItemMeta(IM);
			CommandIcon icon = new CommandIcon(role_panel, icon_item);
			icon.getDataContainer().put("Flag", flag);
			icon.setCommand(EFW); //FIX FIX FIX
			if(y <= 4) role_panel.addIcon(x, y, icon);
			if(y > 4) role_panel2.addIcon(x, y-4, icon);
			if(x >= 7) {x = 0; y += 1;}
			x += 1;
		}
		HierarchyIcon back_icon = new HierarchyIcon(role_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		role_panel.addIcon(4, 5, back_icon);
		if(y > 4) {role_panel2.addIcon(4, 5, back_icon); role_panel.addPage(role_panel2);}
		return role_panel;
		
	}
	
	public InterfacePanel generateDIV() {
		final InterfacePanel invite_panel = new InterfacePanel(5, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Island Invites");
		invite_panel.fill(Material.BLACK_STAINED_GLASS_PANE);
		for(int x = 3; x < 6; x++) {
			for(int y = 0; y < 5; y++) {
				invite_panel.removeIcon(x, y);
			}
		}
		return invite_panel;
	}
	
	public InterfacePanel generateDMP(Map<String, String> members) {
		final InterfacePanel member_panel = new InterfacePanel(6, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Island Members");
		member_panel.addBorder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE);
		int num = 0;
		int x = 1;
		int y = 1;
		member_panel.addParent(this.DIP);
		for(Map.Entry<String, String> entry : members.entrySet()) {
			if(x>7) {x = 1; y += 1;}
			String uuid = entry.getKey();
			ItemStack head = ItemUtility.createHead(UUID.fromString(uuid), num+1);
			head = ItemUtility.setName(head, ChatColor.RESET + "" + ChatColor.of("#FFD502") + "" + ChatColor.BOLD + Utility.getOfflineName(uuid));
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "> " + StringUtils.capitalize(entry.getValue().toLowerCase()));
			head = ItemUtility.setLore(head, lore);
			CommandIcon member_icon = new CommandIcon(member_panel, head);
			member_icon.getDataContainer().put("Player", uuid);
			member_icon.getDataContainer().put("Head", head);
			member_icon.setCommand(new MPWrapper());
			member_panel.addIcon(x, y, member_icon);
			x += 1;
			num += 1;
		}
		HierarchyIcon back_icon = new HierarchyIcon(member_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		member_panel.addIcon(4, 5, back_icon);
		return member_panel;
	}
	
	public InterfacePanel generateDEP(InterfacePanel base) {
		final InterfacePanel env_panel = base;
		env_panel.addBorder(Material.BLUE_STAINED_GLASS_PANE);
		
		ToggleIcon weather_icon = new ToggleIcon(env_panel, new ItemStack(Material.SUNFLOWER), ChatColor.GREEN + "Change Island Weather", new ToggleData("Default", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Current Weather: " + ChatColor.GREEN + "Default", "default"));
		weather_icon.addToggle(new ToggleData("Clear", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Current Weather: " + ChatColor.GREEN + "Clear", "clear"));
		weather_icon.addToggle(new ToggleData("Downfall", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Current Weather: " + ChatColor.GREEN + "Downfall", "downfall"));
		weather_icon.setCommand(new IslandWeatherWrapper());
		env_panel.addIcon(2, 2, weather_icon);
		
		InputIcon icon = new InputIcon(env_panel, new ItemStack(Material.CLOCK), ChatColor.GOLD + "Change Island Time", "Right click to reset to default.");
		icon.setCommand((InputCommandWrapper) new IslandTimeWrapper());
		icon.setRightCommand(new IslandTimeWrapper());
		icon.setExtraneous("reset");
		icon.quickSet("Enter Time (0-24000)", "Input", Material.CLOCK);
		env_panel.addIcon(4, 2, icon);
		
		HierarchyIcon biome_icon = new HierarchyIcon(env_panel, new ItemStack(Material.FILLED_MAP), ChatColor.DARK_PURPLE + "Change Island Biome");
		biome_icon.setHandler("forward", this.baseBP.getUUID());
		env_panel.addIcon(6, 2, biome_icon);
		env_panel.addChild(this.baseBP);
		
		HierarchyIcon back_icon = new HierarchyIcon(env_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		env_panel.addIcon(4, 4, back_icon);
		return env_panel;
	}
	
	public InterfacePanel updateDMP(InterfacePanel old_panel, Map<String, String> members) {
		final InterfacePanel member_panel = old_panel;
		old_panel.clear();
		member_panel.addBorder(Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE);
		int num = 0;
		int x = 1;
		int y = 1;
		for(Map.Entry<String, String> entry : members.entrySet()) {
			if(x>7) {x = 1; y += 1;}
			String uuid = entry.getKey();
			ItemStack head = ItemUtility.createHead(UUID.fromString(uuid), num+1);
			head = ItemUtility.setName(head, ChatColor.RESET + "" + ChatColor.of("#FFD502") + "" + ChatColor.BOLD + Utility.getOfflineName(uuid));
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "> " + StringUtils.capitalize(entry.getValue().toLowerCase()));
			head = ItemUtility.setLore(head, lore);
			CommandIcon member_icon = new CommandIcon(member_panel, head);
			member_icon.getDataContainer().put("Player", uuid);
			member_icon.getDataContainer().put("Head", head);
			member_icon.setCommand(new MPWrapper());
			member_panel.addIcon(x, y, member_icon);
			x += 1;
			num += 1;
		}
		HierarchyIcon back_icon = new HierarchyIcon(member_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		member_panel.addIcon(4, 5, back_icon);
		return member_panel;
	}
	
	public InterfacePanel generateBPanel(InterfacePanel base) {
		InterfacePanel biome_panel = base;
		biome_panel.addBorder(Material.BLUE_STAINED_GLASS_PANE);
		
		CommandIcon plain_icon = new CommandIcon(biome_panel, new ItemStack(Material.GRASS_BLOCK), ChatColor.GREEN + "" + ChatColor.BOLD + "Plains Biome", ChatColor.RESET + "Set biome to plains.");
		plain_icon.setCommand(this.IBW);
		plain_icon.setExtraneous("plains");
		biome_panel.addIcon(1, 1, plain_icon);
		
		CommandIcon desert_icon = new CommandIcon(biome_panel, new ItemStack(Material.SAND), ChatColor.GREEN + "" + ChatColor.BOLD + "Desert Biome", ChatColor.RESET + "Set biome to desert.");
		desert_icon.setCommand(this.IBW);
		desert_icon.setExtraneous("desert");
		biome_panel.addIcon(2, 1, desert_icon);
		
		CommandIcon snow_icon = new CommandIcon(biome_panel, new ItemStack(Material.SNOW_BLOCK), ChatColor.GREEN + "" + ChatColor.BOLD + "Snowy Tundra Biome", ChatColor.RESET + "Set biome to snowy tundra.");
		snow_icon.setCommand(this.IBW);
		snow_icon.setExtraneous("snowy tundra");
		biome_panel.addIcon(3, 1, snow_icon);
		
		CommandIcon nether_icon = new CommandIcon(biome_panel, new ItemStack(Material.NETHERRACK), ChatColor.GREEN + "" + ChatColor.BOLD + "Nether Biome", ChatColor.RESET + "Set biome to the nether.");
		nether_icon.setCommand(this.IBW);
		nether_icon.setExtraneous("nether wastes");
		biome_panel.addIcon(4, 1, nether_icon);
		
		CommandIcon end_icon = new CommandIcon(biome_panel, new ItemStack(Material.END_STONE), ChatColor.GREEN + "" + ChatColor.BOLD + "End Biome", ChatColor.RESET + "Set biome to the end (sky).");
		end_icon.setCommand(this.IBW);
		end_icon.setExtraneous("end");
		biome_panel.addIcon(5, 1, end_icon);
		
		CommandIcon swamp_icon = new CommandIcon(biome_panel, new ItemStack(Material.SLIME_BALL), ChatColor.GREEN + "" + ChatColor.BOLD + "Swamp Biome", ChatColor.RESET + "Set biome to swamp.");
		swamp_icon.setCommand(this.IBW);
		swamp_icon.setExtraneous("swamp");
		biome_panel.addIcon(6, 1, swamp_icon);
		
		CommandIcon mushroom_icon = new CommandIcon(biome_panel, new ItemStack(Material.BROWN_MUSHROOM), ChatColor.GREEN + "" + ChatColor.BOLD + "Mushroom Island Biome", ChatColor.RESET + "Set biome to mushroom island.");
		mushroom_icon.setCommand(this.IBW);
		mushroom_icon.setExtraneous("mushroom fields");
		biome_panel.addIcon(7, 1, mushroom_icon);
		
		CommandIcon flower_icon = new CommandIcon(biome_panel, new ItemStack(Material.ORANGE_TULIP), ChatColor.GREEN + "" + ChatColor.BOLD + "Flower Forest Biome", ChatColor.RESET + "Set biome to flower forest.");
		flower_icon.setCommand(this.IBW);
		flower_icon.setExtraneous("flower forest");
		biome_panel.addIcon(1, 2, flower_icon);
		
		CommandIcon forest_icon = new CommandIcon(biome_panel, new ItemStack(Material.OAK_SAPLING), ChatColor.GREEN + "" + ChatColor.BOLD + "Forest Biome", ChatColor.RESET + "Set biome to forest.");
		forest_icon.setCommand(this.IBW);
		forest_icon.setExtraneous("forest");
		biome_panel.addIcon(2, 2, forest_icon);
		
		CommandIcon taiga_icon = new CommandIcon(biome_panel, new ItemStack(Material.SPRUCE_SAPLING), ChatColor.GREEN + "" + ChatColor.BOLD + "Taiga Biome", ChatColor.RESET + "Set biome to taiga.");
		taiga_icon.setCommand(this.IBW);
		taiga_icon.setExtraneous("taiga");
		biome_panel.addIcon(3, 2, taiga_icon);
		
		CommandIcon jungle_icon = new CommandIcon(biome_panel, new ItemStack(Material.JUNGLE_SAPLING), ChatColor.GREEN + "" + ChatColor.BOLD + "Jungle Biome", ChatColor.RESET + "Set biome to jungle.");
		jungle_icon.setCommand(this.IBW);
		jungle_icon.setExtraneous("jungle");
		biome_panel.addIcon(4, 2, jungle_icon);
		
		CommandIcon ocean_icon = new CommandIcon(biome_panel, new ItemStack(Material.WATER_BUCKET), ChatColor.GREEN + "" + ChatColor.BOLD + "Ocean Biome", ChatColor.RESET + "Set biome to ocean.");
		ocean_icon.setCommand(this.IBW);
		ocean_icon.setExtraneous("ocean");
		biome_panel.addIcon(5, 2, ocean_icon);
		
		HierarchyIcon back_icon = new HierarchyIcon(biome_panel, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		biome_panel.addIcon(4, 4, back_icon);
		return biome_panel;
	}
	
	public InterfacePanel getDebugPanel() {
		return this.debugPanel;
	}
	
	public InterfacePanel getNoIslandPanel() {
		return this.NIP;
	}
	
	public InterfacePanel getIslandSettingsPanel() {
		return this.ISP;
	}
	
	public InterfacePanel getDefaultRolePanel() {
		return this.DRP.clone();
	}
	
	public InterfacePanel getDefaultIslandPanel() {
		return this.DIP;
	}
		
	public InterfacePanel getDefaultInvitePanel() {
		return this.DIV.clone();
	}
	
	public InterfacePanel getDefaultMemberPanel() {
		return this.DMP.clone();
	}

	public InterfacePanel getDefaultEnvironmentPanel() {
		return this.DEP.clone();
	}
	
	public List<String> permissionsToLore(Map<Flag, List<String>> flags, Flag flag) {
		List<String> entry_list = flags.get(flag);
		return permissionsToLore(flag.getName(), entry_list);
	}
	
	public List<String> permissionsToLore(String name, List<String> entry_list) {
		List<String> lore = new ArrayList<String>();
		String description = (String) skyconomy.getFlagManager().getFlagDescriptions().get(name);
		if(description.contains("!")) {
			String[] qargs = description.split("!");
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + qargs[0]);
			description = qargs[1];
		}
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + description);
		lore.add(" ");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "The current settings for this flag are:");
		if(entry_list != null) {
			if(entry_list.contains("ENABLED")) {lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled"); return lore;}
			if(entry_list.contains("DISABLED")) {lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Disabled"); return lore;}
			if(entry_list.contains("OWNER")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Owner: " + ChatColor.GREEN + "Allowed");
			if(!entry_list.contains("OWNER")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Owner: " + ChatColor.RED + "Disallowed");
			if(entry_list.contains("ADMIN")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Admins: " + ChatColor.GREEN + "Allowed");
			if(!entry_list.contains("ADMIN")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Admins: " + ChatColor.RED + "Disallowed");
			if(entry_list.contains("MEMBER")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Members: " + ChatColor.GREEN + "Allowed");
			if(!entry_list.contains("MEMBER")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Members: " + ChatColor.RED + "Disallowed");
			if(entry_list.contains("GUEST")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Guests: " + ChatColor.GREEN + "Allowed");
			if(!entry_list.contains("GUEST")) lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "> Guests: " + ChatColor.RED + "Disallowed");
		}
		return lore;
	}
	
	public ItemStack getHead(String SkullName, String SkullID, String texture_value) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		NBTItem nbti = new NBTItem(head);
		NBTCompound skull = nbti.addCompound("SkullOwner");
		skull.setString("Name", SkullName);
		skull.setString("Id", SkullID);
		NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
		texture.setString("Value", texture_value);
		head = nbti.getItem();
		return head;
	}

}
