package me.imamapletree.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.imamapletree.tools.MatCheck;
import me.imamapletree.tools.Utility;

public class SpawnEggListener implements Listener{
	public SpawnEggListener() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return; //Could lead to issues because potentially a mob could be spawned in air? (I doubt it)
		if(MatCheck.isSpawnEgg(event.getPlayer().getEquipment().getItemInMainHand().getType()) || MatCheck.isSpawnEgg(event.getPlayer().getEquipment().getItemInOffHand().getType())) {
			if(Utility.SimpleACheck("AllowSpawnEgg", event.getPlayer(), event.getClickedBlock().getLocation())) return;
			event.setCancelled(true);
			return;
		} return;
	}
}
