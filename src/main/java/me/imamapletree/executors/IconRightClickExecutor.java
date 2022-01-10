package me.imamapletree.executors;

import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.panels.Icon;

public class IconRightClickExecutor implements Runnable{
	private Skyconomy skyconomy;
	private Player player;
	private Icon icon;
	
	public IconRightClickExecutor(final Player player, final Icon icon) {
		this.player = player;
		this.icon = icon;
		this.skyconomy = Skyconomy.getInstance();
	}
	
	@Override
	public void run() {
		final boolean close = this.icon.onRightClick(player);
		if (close) {
			skyconomy.getMiscManager().removeIAP(player.getUniqueId().toString());
			this.player.closeInventory();
		}
	}
}
