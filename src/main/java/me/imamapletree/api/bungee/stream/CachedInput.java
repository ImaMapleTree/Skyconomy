package me.imamapletree.api.bungee.stream;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedInput {
	private List<String[]> playerlists = new ArrayList<String[]>();
	private Map<String, String> UUIDmap = new HashMap<String, String>();
	private Map<String, String> Servermap = new HashMap<String, String>();
	private boolean rewrite = true;
	
	public CachedInput() {
	}

	public List<String> getPlayerlist() {
		List<String> megalist = new ArrayList<String>();
		for(String[] slist : playerlists) {
			for(String player : slist) {
				megalist.add(player);
			}
		}
		return megalist;
	}
	
	public String getPlayerUUID(String name) {
		return this.UUIDmap.get(name);
	}
	
	public String getServer(String name) {
		return this.Servermap.get(name);
	}
	
	public void enableRewrite() {
		this.rewrite = true;
	}
	
	public void recieveCachedUUID(String name, String uuid) {
		UUIDmap.put(name, uuid);
	}
	
	public void recieveCachedServer(String name, String server) {
		this.Servermap.put(name, server);
	}
	
	public void recieveCachedList(String[] playerlist) {
		if(!this.rewrite) this.playerlists.add(playerlist);
		else {
			this.playerlists = new ArrayList<String[]>();
			this.playerlists.add(playerlist);
			this.rewrite = false;
		}
	}
}
