package me.imamapletree.events.listeners.blocks.addons;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class UseAutoCraftingTable implements Listener{
	public UseAutoCraftingTable() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock().getType() != Material.DISPENSER) return;
		
		
		//if(Util.SimpleACheck("UseAutoCraftingTable", event.getPlayer(), event.getClickedBlock().getLocation())) return;
		//event.setCancelled(true);
		return;
	}
}
