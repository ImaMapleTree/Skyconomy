package me.imamapletree.wrappers.panel;

import org.bukkit.entity.Player;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.Icon;

public class IslandTimeWrapper implements InputCommandWrapper {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	
	public boolean onClick(Icon icon, Player player, String input) {
		Long time;
		boolean retvalue = true;
		if(input.equals("reset")) {
			player.resetPlayerTime();
			time = -1L;
			retvalue = false;
		}
		else {
			try {
				if(input.equals("I")) input = "-1";
				time = Long.valueOf(input);
				if (time > 24000) return false;
				player.setPlayerTime(time, false);
			} catch (NumberFormatException error) {
				return false;
			}
		}
		Island island = skyconomy.getMiscManager().getIAP(player.getUniqueId().toString());
		island.setDefaultTime(time);
		return retvalue;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
