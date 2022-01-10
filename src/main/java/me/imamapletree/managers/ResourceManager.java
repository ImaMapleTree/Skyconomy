package me.imamapletree.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

import me.imamapletree.Settings;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.database.DataFolder;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.JsonHelper;

public class ResourceManager {
	private Skyconomy skyconomy;
	private final File cache = new DataFolder("data\\cache").getFile();
	public final File SCHEMATICS_FOLDER = new DataFolder("schematics").getFile();
	private final JSONObject DEFAULT_PLAYER_JSON = JsonHelper.loadJsonFromResources("DefaultPlayer.json");
	private final JSONObject DEFAULT_ISLAND_JSON = JsonHelper.loadJsonFromResources("DefaultIsland.json");
	private final JSONObject TEMPLATE_GLOBAL = JsonHelper.loadJsonFromResources("TemplateGlobal.json");
	private JSONObject GLOBAL_ISLAND_JSON = JsonHelper.loadNonExistingJson(cache, TEMPLATE_GLOBAL, "global_island_data.json");
	private Settings settings;
	private List<File> ghost_files = new ArrayList<File>();
	
	public ResourceManager(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		this.settings = skyconomy.settings;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getGlobalIslandData(String world) {
		JSONObject JO = JsonHelper.loadNonExistingJson(DataFolder.addBranch(cache, world), this.TEMPLATE_GLOBAL, "global_island_data.json");
		FlatCoordinate IC = CoordinateParse.FlatCoordParse((String) JO.get("maxCorner"));
		if(IC == null) {
			IC = new FlatCoordinate(0, 0);
			JO.put("maxCorner", CoordinateParse.toString(IC.clone().add(this.settings.SpawnOffset, this.settings.SpawnOffset)));
			JO.put("minCorner", CoordinateParse.toString(IC.clone().subtract(this.settings.SpawnOffset, this.settings.SpawnOffset)));
		}
		return JO;
	}
	
	public JSONObject getDefaultPlayer() {
		return (JSONObject) this.DEFAULT_PLAYER_JSON.clone();
	}
	
	public JSONObject getDefaultIsland() {
		return (JSONObject) this.DEFAULT_ISLAND_JSON.clone();
	}
	
	public void saveAllGlobalData() {
		for (String world : skyconomy.getEnabledWorlds()) {
			saveGlobalData(world, skyconomy.getIslandManager().getGlobalData(world).getChild());
		}
	}
	
	public void saveGlobalData(String world, JSONObject JO) {
		JsonHelper.saveJson(DataFolder.addBranch(cache, world), JO, "global_island_data.json");
	}
	
	public void saveNewGlobalData(String world) {
		JsonHelper.saveNewJson(DataFolder.addBranch(cache, world), this.GLOBAL_ISLAND_JSON, "global_island_data.json");
	}

	public void awaitDelete(String Sfile) {
		ghost_files.add(new File(Sfile));
	}
	
	public void awaitDelete(File file) {
		ghost_files.add(file);
	}
	
	public Integer deleteFiles() {
		for(File file : ghost_files) {
			file.delete();
		}
		return ghost_files.size();
	}
}
