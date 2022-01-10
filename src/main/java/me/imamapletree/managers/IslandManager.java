package me.imamapletree.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sk89q.worldedit.WorldEditException;

import me.imamapletree.intensives.DeleteChunks;
import me.imamapletree.regions.Region;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.JsonHelper;
import me.imamapletree.tools.Schematics;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.Settings;
import me.imamapletree.Skyconomy;
import me.imamapletree.WorldLinker;
import me.imamapletree.database.DataFolder;
import me.imamapletree.api.ChildJSON;
import me.imamapletree.api.ChunkCoordinate;
import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.api.instances.Island;
import me.imamapletree.tools.builders.SentenceBuilder;

public class IslandManager {
	private static Skyconomy skyconomy;
	public static File directory = new DataFolder("data\\islands").getFile();
	private Map<FlatCoordinate, Island> islands = new HashMap<FlatCoordinate, Island>();
	private List<FlatCoordinate> island_centers = new ArrayList<FlatCoordinate>();
	private List<Island> needs_caching = new ArrayList<Island>();
	public Map<String, JSONObject> world_data = new HashMap<String, JSONObject>();
	private static IslandManager manager;
	private Settings settings;
	
	@SuppressWarnings("static-access")
	public IslandManager(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		this.manager = this;
		this.settings = skyconomy.getSettings();
		for (String EWorld : WorldLinker.getInstance().getAllWorlds()) {
			world_data.put(EWorld, manager.loadGlobalData(EWorld));
		}
		
	}
	
	public Island createIsland(Player player) {
		String world = player.getWorld().getName();
		File newdirectory = DataFolder.addBranch(directory, world);
		ChildJSON GlobalData = manager.getGlobalData(world);
		Location loc = WorldCoordinate.createLocation(player.getWorld(), manager.nextCoordinate(world), settings.island_y);
		GlobalData.put("islands", (int) JsonHelper.readAsInt(GlobalData.getChild(), "islands")+1);
		Island IS = new Island(loc, player);
		IS.setPath(JsonHelper.appendExtension(newdirectory, IS.getUUID()));
		try {
			Schematics.load(loc, Schematics.createFileClipboard("basic_island"));
		} catch (IOException | WorldEditException e) {
			SkyLog.log(e);
			return null;
		}
		player.teleport(IS.getTPBlock());
		manager.addToCache(IS);
		manager.add(IS);
		return IS;
	}
	
	public Island deleteIsland(Player player) {
		Island IS = getIsland(player.getUniqueId().toString(), player.getWorld().getName());
		return this.deleteIsland(IS);
	}
	
	public Island deleteIsland(Island IS) { //Perhaps delete all the islands on close and then open the coordinates up for re-writing
		Region R = new Region(IS.getCoordinate().clone().subtract(250, 250), IS.getCoordinate().clone().add(250, 250));
		ChunkCoordinate[] chunks = R.getChunks();
		this.island_centers.remove(manager.island_centers.indexOf(IS.getCoordinate()));
		int cached_index = manager.needs_caching.indexOf(IS);
		if(cached_index != -1) manager.needs_caching.remove(cached_index);
		this.islands.remove(IS.getCoordinate());
		this.forceCoord(IS.getWorld().getName(), IS.getCoordinate());
		skyconomy.getResourceManager().awaitDelete(IS.getPath());
		IS.flushData();
		Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, (Runnable)new DeleteChunks(IS.getWorld(), chunks));
		return IS;
	}
	
	public void deleteFromCache(FlatCoordinate FC) {
		this.island_centers.remove(FC);
		this.islands.remove(FC);
	}
	
	public FlatCoordinate nextCoordinate(World world) {
		return nextCoordinate(world.getName());
	}

	public void addToCache(Island island) {
		if(this.needs_caching.contains(island)) return;
		this.needs_caching.add(island);
	}
	
	public void add(Island island) {
		this.island_centers.add(island.getCoordinate());
		SkyLog.log("Island:");
		SkyLog.log(island);
		this.islands.put(island.getCoordinate(), island);
	}
	
	public Island locateIsland(Location location){
		int range = skyconomy.getSettings().BLOCKS_FROM_CENTER;
		FlatCoordinate IC = new FlatCoordinate(location);
		Optional<FlatCoordinate> OIC = this.island_centers.stream().filter(island -> IC.isLessX(island.addX(range, true))&&IC.isGreaterX(island.subtractX(range, true))&&IC.isLessZ(island.addZ(range, true))&&IC.isGreaterZ(island.subtractZ(range, true))).findAny();
		if (!OIC.isPresent()) return null;
		Island IS = islands.get(OIC.get());
		return IS;
	}
	
	public Island getIsland(String ownerID, World world) {
		return getIsland(ownerID, world.getName());
	}
	
	public Island getIsland(String ownerID, String world) {
		//Optional<Island> IS = this.islands.entrySet().stream().filter(island -> island.getValue().getOwner().equals(ownerID)&&island.getValue().getWorld().getName().equals(world)).findAny().map(map -> map.getValue());
		Optional<Island> IS = this.islands.entrySet().stream().filter(island -> island.getValue().getOwner().equals(ownerID)).findAny().map(map -> map.getValue());
		if (!IS.isPresent()) return (Island) null;
		return IS.get();
	}

	public Island getIsland(String iUID) {
		Optional<Island> IS = this.islands.entrySet().stream().filter(island -> island.getValue().getUUID().equals(iUID)).findAny().map(map -> map.getValue());
		if (!IS.isPresent()) return (Island) null;
		return IS.get();
	}
	
	public ChildJSON getGlobalData(World world) {
		return getGlobalData(world.getName());
	}
	
	public ChildJSON getGlobalData(String world) {
		for(Map.Entry<String, JSONObject> wd : world_data.entrySet()) SkyLog.log(wd.getKey());
		JSONObject JO = world_data.get(world);
		return new ChildJSON(world_data, world, JO);
	}
	
	private JSONObject loadGlobalData(String world) {
		JSONObject JO = skyconomy.getResourceManager().getGlobalIslandData(world);
		return JO;
	}
	
	public List<Integer> saveCachedIslands() {
		int creation_saved = 0;
		int modify_saved = 0;
		List<Integer> ints = new ArrayList<Integer>();
		for (Island island : needs_caching) {
			try {
				if(island == null) continue;
				if(island.getCenter().getWorld() == null) continue;
				island.saveData();
				if(island.getCReason() == "creation") creation_saved += 1;
				else modify_saved += 1;
			} catch (Error e) {
				SkyLog.slog(e);
			}
		}
		ints.add(creation_saved);
		ints.add(modify_saved);
		return ints;
	}
	
	public void loadIslands(String world) {
		File newdirect = DataFolder.addBranch(directory, world);
		for (File file : newdirect.listFiles()) {
			JSONObject data = JsonHelper.loadJson(file);
			Island island = new Island(data);
			island.setPath(file);
			this.island_centers.add(island.getCoordinate());
			this.islands.put(island.getCoordinate(), island);
		} SkyLog.log(new SentenceBuilder("Successfully loaded").add(newdirect.listFiles().length).add("islands from World").add(world).create());
	}
	
	@SuppressWarnings("unchecked")
	public void forceCoord(String world, FlatCoordinate IC) {
		JSONObject GData = world_data.get(world);
		JSONArray pcoords = (JSONArray) GData.get("pushedcoords");
		pcoords.add(CoordinateParse.toString(IC));
		GData.put("pushedcoords", pcoords);
		world_data.put(world, GData);
	}
	
    @SuppressWarnings("unchecked")
	public FlatCoordinate nextCoordinate(String world) {
		JSONObject GData = world_data.get(world);
		FlatCoordinate maxCorner = CoordinateParse.FlatCoordParse((String) GData.get("maxCorner"));
		FlatCoordinate minCorner = CoordinateParse.FlatCoordParse((String) GData.get("minCorner"));
		boolean bot = (boolean) GData.get("bot");
		boolean right = (boolean) GData.get("right");
		boolean top = (boolean) GData.get("top");
		boolean left = (boolean) GData.get("left");
	    FlatCoordinate cachedcoord = CoordinateParse.FlatCoordParse((String) GData.get("cachedcoord"));
	    JSONArray pcoords = (JSONArray) GData.get("pushedcoords");
	    if (!pcoords.isEmpty()) {
	    	String IString = (String) pcoords.remove(0);
	    	GData.put("pushedcoord", pcoords);
	    	world_data.put("world", GData);
	    	return CoordinateParse.FlatCoordParse((String) IString);
	    }
		if (cachedcoord == null) {
			cachedcoord = minCorner.clone();
		}
		if(bot == false) {
			cachedcoord.add(1000, 0);
			if(cachedcoord.equalsX(maxCorner)) bot = true;
		}
		else if(right == false) {
			cachedcoord.add(0, 1000);
			if(cachedcoord.equalsZ(maxCorner)) right = true;
		}
		else if(top == false) {
			cachedcoord.subtract(1000, 0);
			if(cachedcoord.equalsX(minCorner)) top = true;
		}
		else if(left == false) {
			cachedcoord.subtract(0, 1000);
			if(cachedcoord.equalsZ(minCorner)) left = true;
		} else {
			cachedcoord = null;
			bot = false;
			right = false;
			top = false;
			left = false;
			maxCorner.add(1000, 1000);
			minCorner.add(-1000, -1000);
			GData.put("maxCorner", CoordinateParse.toString(maxCorner));
			GData.put("minCorner", CoordinateParse.toString(minCorner));
			GData.put("bot", bot);
			GData.put("right", right);
			GData.put("top", top);
			GData.put("left", left);
			GData.put("cachedcoord", CoordinateParse.toString(cachedcoord));
			world_data.put("world", GData);
			return nextCoordinate(world);
		}
		GData.put("maxCorner", CoordinateParse.toString(maxCorner));
		GData.put("minCorner", CoordinateParse.toString(minCorner));
		GData.put("bot", bot);
		GData.put("right", right);
		GData.put("top", top);
		GData.put("left", left);
		GData.put("cachedcoord", CoordinateParse.toString(cachedcoord));
		world_data.put("world", GData);
		return cachedcoord;
	 }
}