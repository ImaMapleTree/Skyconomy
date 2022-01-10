package me.imamapletree.wrappers.panel;

import java.util.List;

import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.ContainerKey;
import me.imamapletree.api.bungee.bridge.IslandBridge;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;

public class ToggleGlobalFlagWrapper implements ToggleCommandWrapper {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	
	public boolean onClick(Icon icon, Player player, Object toggle) {
		if(!(toggle instanceof String)) return false;
		Island island = (Island) icon.getPanel().getDataContainer().get(new ContainerKey("Island", null));
		if(island == null) return false;
		String string = (String) toggle;
		Flag flag = skyconomy.getFlagManager().getFlag(icon.getExtraneous()).orElse(null);
		if(flag == null) return false;
		List<String> value = island.getFlagValue(flag);
		value.set(0, string);
		island.setFlagValue(flag, value);
		IslandBridge.sendIsland(island);
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
