package me.imamapletree.api.bungee.commands;

public class DelayWarp implements Runnable {
	private String WCS;
	private String playerid;
	
	public DelayWarp(String playerid, String WCS) {
		this.playerid = playerid;
		this.WCS = WCS;
	}
	
	@Override
	public void run() {
		BIslandWarp.teleport(playerid, WCS);
	}
}
