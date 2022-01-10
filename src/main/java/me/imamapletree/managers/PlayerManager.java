package me.imamapletree.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.database.DataFolder;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.builders.SentenceBuilder;

public class PlayerManager {
	private File player_folder = new DataFolder("data\\players").getFile();
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	private Map<String, String> session_players_surface = new HashMap<String, String>();
	private List<SkyPlayer> skyplayers_players_internal = new ArrayList<SkyPlayer>();
	private List<SkyPlayer> session_players_internal = new ArrayList<SkyPlayer>();
	private List<SkyPlayer> needs_caching = new ArrayList<SkyPlayer>();
	
	public PlayerManager(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	public void add(Player player) {
		SkyPlayer skyplayer = new SkyPlayer(player);
		skyplayers_players_internal.add(skyplayer);
		session_players_surface.put(skyplayer.getName(), skyplayer.getUUID());
		if(!this.session_players_internal.contains(skyplayer)) session_players_internal.add(skyplayer);
	}
	
	public void add(SkyPlayer skyplayer) {
		skyplayers_players_internal.add(skyplayer);
		session_players_surface.put(skyplayer.getName(), skyplayer.getUUID());
		if(!this.session_players_internal.contains(skyplayer)) session_players_internal.add(skyplayer);
	}
	
	public void addSession(Player player) {
		SkyPlayer skyplayer = this.getSkyPlayer(player.getUniqueId()).orElse(null);
		if(skyplayer == null) skyplayer = new SkyPlayer(player);
		if(!this.session_players_internal.contains(skyplayer)) this.session_players_internal.add(skyplayer);
	}
	
	public File getPlayerFolder() {
		return this.player_folder;
	}
	
	public Optional<SkyPlayer> getSkyPlayer(UUID id) {
		return this.skyplayers_players_internal.stream().filter(player -> player.getAUUID().equals(id)).findFirst();
	}
	
	public Optional<SkyPlayer> getSkyPlayer(String name) {
		return this.skyplayers_players_internal.stream().filter(player -> player.getName().equalsIgnoreCase(name)).findFirst();
	}
	
	public List<SkyPlayer> getSkyPlayers() {
		return this.skyplayers_players_internal;
	}
	
	public List<SkyPlayer> getSessionPlayers() {
		return this.session_players_internal;
	}
	
	public Map<String, String> getSurfacePlayers() {
		return this.session_players_surface;
	}
	
	public void loadPlayers() {
		for (File file : player_folder.listFiles()) {
			SkyPlayer skyplayer = new SkyPlayer(file);
			skyplayers_players_internal.add(skyplayer);
			session_players_surface.put(skyplayer.getName(), skyplayer.getUUID());
		} SkyLog.log(new SentenceBuilder("Successfully loaded").add(player_folder.listFiles().length).add("players from storage.").create());
	}
	
	public void addToCache(SkyPlayer player) {
		if(needs_caching.contains(player) == false) needs_caching.add(player);
	}
	
	public List<Integer> saveCachedPlayers() {
		int creation_saved = 0;
		int modify_saved = 0;
		List<Integer> ints = new ArrayList<Integer>();
		for (SkyPlayer player : needs_caching) {
			player.saveData();
			if(player.getCReason() == "creation") creation_saved += 1;
			else modify_saved += 1;
		}
		ints.add(creation_saved);
		ints.add(modify_saved);
		return ints;
	}
}
