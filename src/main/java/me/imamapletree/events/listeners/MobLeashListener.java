package me.imamapletree.events.listeners;

import org.bukkit.Material;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.imamapletree.tools.Utility;

public class MobLeashListener implements Listener{
	public MobLeashListener() {
	}
	
	@EventHandler
	public void onMobClick(PlayerInteractEntityEvent event) {
		if(event.getPlayer().getEquipment().getItemInMainHand().getType() == Material.LEAD || event.getPlayer().getEquipment().getItemInOffHand().getType() == Material.LEAD) {
			if(Utility.SimpleACheck("AllowMobLeashing", event.getPlayer(), event.getRightClicked().getLocation())) return;
			event.setCancelled(true);
			return;
		} return;
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof LeashHitch)) return;
		if(Utility.SimpleACheck("AllowMobLeashing", event.getPlayer(), event.getRightClicked().getLocation())) return;
		event.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void onLeashBreak(HangingBreakByEntityEvent event) {
		if(!(event.getEntity() instanceof LeashHitch)) return;
		if(!(event.getRemover() instanceof Player)) return;
		Player player = ((Player) event.getRemover());
		if(Utility.SimpleACheck("AllowMobLeashing", player, event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
