package me.imamapletree.wrappers.panel;

import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;

public class EditFlagWrapper implements ClickCommandWrapper {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy = Skyconomy.getInstance();
	public boolean onClick(Icon icon, Player player) {
		Flag CFlag = (Flag) icon.getDataContainer().get("Flag");
		//This is a test but I'm going to get the Island from the player throwing the wrapper (right now just using owner, and open the panel that way)
		Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
		//ContainerKey key = new ContainerKey("Island", skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld()));
		//Island island = (Island) icon.getPanel().getDataContainer().get(new ContainerKey(player.getWorld().getName(), player.getUniqueId().toString(), "Island"));
		island.openFlagPanel(icon.getPanel(), CFlag, player);
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
