package me.imamapletree;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import me.imamapletree.api.ChunkCoordinate;
import me.imamapletree.api.CustomColor;
import me.imamapletree.api.SurfaceInventory;
import me.imamapletree.api.bungee.stream.ByteInput;
import me.imamapletree.api.bungee.stream.CachedInput;
import me.imamapletree.api.instances.Island;
import me.imamapletree.database.mobs.CustomEntityType;
import me.imamapletree.database.mobs.SpiderBoss;
import me.imamapletree.managers.DungeonManager;
import me.imamapletree.managers.EventManager;
import me.imamapletree.managers.FlagManager;
import me.imamapletree.managers.IslandManager;
import me.imamapletree.managers.MiscManager;
import me.imamapletree.managers.PlayerManager;
import me.imamapletree.managers.ResourceManager;
import me.imamapletree.tools.JsonHelper;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R2.DataConverterRegistry;
import net.minecraft.server.v1_16_R2.DataConverterTypes;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.EntityTypes.b;
import net.minecraft.server.v1_16_R2.EnumCreatureType;
import net.minecraft.server.v1_16_R2.IRegistry;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.SharedConstants;

public class Skyconomy extends JavaPlugin implements Listener, PluginMessageListener {
	private SourceIsland iscomm;
	private Admin AdminExecutor;
	public static Skyconomy instance;
	public List<String> EnabledWorlds;
	private FlagManager flagmanager;
	private ResourceManager resourcemanager;
	private IslandManager islandmanager;
	private PlayerManager playermanager;
	private MiscManager miscmanager;
	@SuppressWarnings("unused")
	private EventManager eventmanager;
	private DungeonManager dungeonmanager;
	public Settings settings;
	private ByteInput byteinput;
	private CachedInput cachedinput;
	public final static HashMap<ChunkCoordinate, World> tracked_chunks = new HashMap<ChunkCoordinate, World>();
	public CustomColor CUSTOMCOLOR = new CustomColor();
	Logger log = getLogger();
	
	@SuppressWarnings("unused")
	private final Map<Island, String> IslandDict = new HashMap<Island, String>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable () {
		//Add NBT-ITEM DEPENDENCY
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		
		instance = this;
		this.settings = new Settings(this);
		this.settings.Initialize();
		
		File file = new File(getDataFolder(), "config.yml");
        if(!file.exists()) {
        	ConfigurationSection WorldSection = this.getConfig().createSection("Worlds");
    		List<World> WL = Bukkit.getServer().getWorlds();
    		List<String> List = new ArrayList<String>();
    		for(World world : WL) {
    			List.add(world.getName());
    			SkyLog.log(world.getName());
    		}
    		WorldSection.addDefault("Skyblock-Enabled", List);
    		this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
        //Get Stuff From Config
  		this.EnabledWorlds = (java.util.List<String>) this.getConfig().getList("Worlds.Skyblock-Enabled");
        
  		 //Managers
  		SkyLog.log(JsonHelper.class);
        this.flagmanager = new FlagManager(this);
        this.resourcemanager = new ResourceManager(this);
        this.islandmanager = new IslandManager(this);
        this.playermanager = new PlayerManager(this);
        this.miscmanager = new MiscManager(this, this.flagmanager);
        this.eventmanager = new EventManager(this);
        this.dungeonmanager = new DungeonManager(this);
        
        this.byteinput = new ByteInput();
        this.cachedinput = new CachedInput();
		
		iscomm = new SourceIsland(this);
		AdminExecutor = new Admin(this);
		
		//Register all commands here
		this.getCommand("island").setExecutor(iscomm);
		this.getCommand("is").setExecutor(iscomm);
		
		//Admin
		this.getCommand("test").setExecutor(AdminExecutor);
		this.getCommand("loadstructure").setExecutor(AdminExecutor);
		this.getCommand("skydebug").setExecutor(AdminExecutor);
		
		
		
		
		//Register everything else
		SkyLog.log(ChatColor.GOLD + "Remember to delete the dev lines in the code haha!");
		
		
		
		
		//Load all files
		for (String world : EnabledWorlds) {
			this.islandmanager.loadIslands(world);
		}
		this.playermanager.loadPlayers();
		
		//Object zombie = new CustomEntityType <SpiderBoss> ("SpiderBoss", SpiderBoss.class, EntityType.SPIDER, EntityZombie::);
		//Ch
	}
	
	@Override
	public void onDisable() {
		this.resourcemanager.saveAllGlobalData();
		SkyLog.slog(ChatColor.YELLOW + "Attempting to save cached objects.");
		List<Integer> saved = this.getPlayerManager().saveCachedPlayers();
		List<Integer> saved2 = this.getIslandManager().saveCachedIslands();
		Integer ghosts = this.getResourceManager().deleteFiles();
		SkyLog.slog(new me.imamapletree.tools.builders.SentenceBuilder(ChatColor.YELLOW + "[Islands]" + ChatColor.RESET).add("Created:").add(saved2.get(0)).add("| Modified:").add(saved2.get(1)).create());
		SkyLog.slog(new me.imamapletree.tools.builders.SentenceBuilder(ChatColor.YELLOW + "[Players]" + ChatColor.RESET).add("Created:").add(saved.get(0)).add("| Modified:").add(saved.get(1)).create());
		SkyLog.slog(new me.imamapletree.tools.builders.SentenceBuilder(ChatColor.YELLOW + "[Ghost Files]" + ChatColor.RESET).add("Deleted:").add(ghosts).create());
		SkyLog.slog(ChatColor.GREEN + "Successfully dumped cache.");
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(!channel.equals("BungeeCord")) return;
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if(Utility.isBungeeSubchannel(subchannel)) byteinput.interpretInput(subchannel, player, in);
		else {DataInputStream DIS = byteinput.readInput(in);
		try {
			byteinput.interpretStream(subchannel, DIS);
		} catch (IOException e) {
			SkyLog.log(e);
		}
		}
	}
	
	public FlagManager getFlagManager() {
		return this.flagmanager;
	}
	
	public ResourceManager getResourceManager() {
		return this.resourcemanager;
	}
	
	public IslandManager getIslandManager() {
		return this.islandmanager;
	}

	public PlayerManager getPlayerManager() {
		return this.playermanager;
	}
	
	public Settings getSettings() {
		return this.settings;
	}
	
	public MiscManager getMiscManager() {
		return this.miscmanager;
	}
	
	public EventManager getEventManager() {
		return this.eventmanager;
	}
	
	public DungeonManager getDungeonManager() {
		return this.dungeonmanager;
	}
	
	public ByteInput getByteInput() {
		return this.byteinput;
	}
	
	public CachedInput getCachedInput() {
		return this.cachedinput;
	}
	
	public void addChunks(World world, ChunkCoordinate[] Coords) {
		for (ChunkCoordinate WC : Coords) {
			tracked_chunks.put(WC, world);
		}
	}
	
	public List<String> getEnabledWorlds() {
		return this.EnabledWorlds;
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap getActiveChunks() {
		return tracked_chunks;
	}
	
	public static Skyconomy getInstance() {
		return instance;
	}
	
	public SurfaceInventory createInventory(InventoryHolder owner, int size, String title) {
		return new SurfaceInventory(Bukkit.createInventory(owner, size, title), title);
	}
}
