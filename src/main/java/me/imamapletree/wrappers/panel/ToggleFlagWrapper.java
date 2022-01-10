package me.imamapletree.wrappers.panel;

import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.bungee.bridge.IslandBridge;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;

public class ToggleFlagWrapper implements ClickCommandWrapper {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	public boolean onClick(Icon icon, Player player) {
		Flag CFlag = (Flag) icon.getDataContainer().get("Flag");
		String role = (String) icon.getDataContainer().get("Role");
		//This is a test but I'm going to get the Island from the player throwing the wrapper (right now just using owner, and open the panel that way)
		//Island island = (Island) icon.getPanel().getDataContainer().get(new ContainerKey("Island", null));
		Island island = skyconomy.getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld());
		island.toggleMember(icon.getPanel(), CFlag, role);
		IslandBridge.sendIsland(island);
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
