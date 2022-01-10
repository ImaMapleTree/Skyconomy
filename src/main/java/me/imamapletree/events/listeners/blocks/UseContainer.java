package me.imamapletree.events.listeners.blocks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.imamapletree.tools.MatCheck;
import me.imamapletree.tools.Utility;

public class UseContainer implements Listener{
	public UseContainer() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(MatCheck.isContainer(event.getClickedBlock().getType()) == false) return;
		if(Utility.SimpleACheck("UseContainer", event.getPlayer(), event.getClickedBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
