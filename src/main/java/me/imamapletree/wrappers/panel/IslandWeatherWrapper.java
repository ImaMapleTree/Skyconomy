package me.imamapletree.wrappers.panel;

import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;

public class IslandWeatherWrapper implements ToggleCommandWrapper {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	
	public boolean onClick(Icon icon, Player player, Object toggle) {
		if(!(toggle instanceof String)) return false;
		Island island = skyconomy.getMiscManager().getIAP(player.getUniqueId().toString());
		if(island == null) return false;
		String string = (String) toggle;
		switch (string) {
			case "clear":
				island.setDefaultWeather("clear");
				break;
			case "downfall":
				island.setDefaultWeather("rain");
				break;
			case "default":
				island.setDefaultWeather("default");
				break;
			default:
				island.setDefaultWeather("default");
				break;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
