package me.imamapletree.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.bukkit.entity.Player;

public class Hash {
	public final static Yaml YML_Handler = new Yaml();
	
	private Hash() {
	}
	
	public static <T> Set<T> newHashSet() {
		return new HashSet<T>();
	}
	
	@SuppressWarnings("hiding")
	public static <Player, Long> Map<Player, Long> newPlayerHashMap() {
        return new HashMap<Player, Long>();
    }
	
	public boolean existsInHash(HashMap<?, ?> map, Object value) {
		return map.containsValue(value);
	}
	 
	public static void HashMapSave(HashMap<?, ?> map, File file) {
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		data.createSection("ISD", map);
		try {
			data.save(file);
		} catch (IOException e) {
			Bukkit.getLogger().info(e.toString());
			Bukkit.getLogger().info("[Critical Error] Unable to save Hashmap, disabling plugin.");
			return;
		}
	}
	 
}
