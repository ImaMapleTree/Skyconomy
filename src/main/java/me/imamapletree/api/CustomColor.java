package me.imamapletree.api;

import net.md_5.bungee.api.ChatColor;

public class CustomColor {
	public ChatColor DARK_CYAN;
	public ChatColor ORANGE;
	public ChatColor MAROON;
	public ChatColor SALMON;
	public ChatColor PINK;
	public ChatColor HOT_PINK;
	public ChatColor HUED_YELLOW;
	public ChatColor RUST;
	public ChatColor REAL_PURPLE;
	
    public CustomColor() {
    	initialize();
    }
    
    private void initialize() {
    	DARK_CYAN = ChatColor.of("#143D59");
    	ORANGE = ChatColor.of("#E76D19");
    	MAROON = ChatColor.of("#5B0E2D");
    	SALMON = ChatColor.of("#FFA781");
    	PINK = ChatColor.of("#F9858B");
    	HOT_PINK = ChatColor.of("#ED335F");
    	HUED_YELLOW = ChatColor.of("#FDFF7B");
    	RUST = ChatColor.of("#722620");
    	REAL_PURPLE = ChatColor.of("#671CA5");
    }
}
