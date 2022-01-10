package me.imamapletree.api.instances;

import net.md_5.bungee.api.ChatColor;

public class Dungeon {
	private String name;
	private Integer rarity;
	
	public Dungeon(String name, Integer rarity) {
		this.name = name;
		this.rarity = rarity;
	}


	public String getName() {
		return this.name;
	}
	
	public Integer getRarity() {
		return this.rarity;
	}
	
	public String getRarityPrefix() {
		if(this.rarity.equals(1)) return ChatColor.DARK_GREEN + "";
		if(this.rarity.equals(2)) return ChatColor.BLUE + "";
		if(this.rarity.equals(3)) return ChatColor.DARK_PURPLE + "";
		return ChatColor.GOLD + "";
	}
}
