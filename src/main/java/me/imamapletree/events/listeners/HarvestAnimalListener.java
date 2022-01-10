package me.imamapletree.events.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.imamapletree.tools.MatCheck;
import me.imamapletree.tools.Utility;

public class HarvestAnimalListener implements Listener{
	public HarvestAnimalListener() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof Animals)) return;
		Player player = event.getPlayer();
		if (MatCheck.isAnimalTool(player.getEquipment().getItemInMainHand().getType()) || MatCheck.isAnimalTool(player.getEquipment().getItemInOffHand().getType())) {
			if(Utility.SimpleACheck("HarvestAnimalProduct", event.getPlayer(), event.getRightClicked().getLocation())) return;
			event.setCancelled(true);
			return;
		} return;
	}
}
