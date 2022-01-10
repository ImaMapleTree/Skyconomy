package me.imamapletree.events.listeners;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.imamapletree.tools.Utility;

public class ChangeItemFrameListener implements Listener{
	public ChangeItemFrameListener() {
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof ItemFrame)) return;
		if(Utility.SimpleACheck("AffectHanging", event.getPlayer(), event.getRightClicked().getLocation())) return;
		event.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void onLeftClick(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof ItemFrame)) return;
		if(!(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		if(Utility.SimpleACheck("AffectHanging", player, event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
