package me.imamapletree.wrappers;

import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;
import me.imamapletree.wrappers.panel.ClickCommandWrapper;

public class GlobalFlagWrapper implements ClickCommandWrapper {
	private Skyconomy skyconomy = Skyconomy.getInstance();	
	
	public boolean onClick(Icon icon, Player player) {
		Island island = skyconomy.getMiscManager().getIAP(player.getUniqueId().toString());
		if(island == null) island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
		if(island == null) return true;
		island.getGlobalPanel().openPanel(player);
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
