package me.imamapletree.events.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.imamapletree.tools.Utility;

public class CropTrampleListener implements Listener{
	public CropTrampleListener() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.PHYSICAL) return;
		if (event.getClickedBlock().getType() != Material.FARMLAND) return;
		if(Utility.SimpleACheck("AllowCropTrample", event.getPlayer(), event.getClickedBlock().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
