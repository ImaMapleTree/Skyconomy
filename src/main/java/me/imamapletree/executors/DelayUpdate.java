package me.imamapletree.executors;

import org.bukkit.entity.Player;

public class DelayUpdate implements Runnable{
	private Player player;
	
	public DelayUpdate(final Player player) {
		this.player = player;
	}
	
	@Override
	public void run() {
		this.player.updateInventory();
	}
}
