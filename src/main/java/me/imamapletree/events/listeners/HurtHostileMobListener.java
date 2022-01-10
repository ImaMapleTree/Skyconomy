package me.imamapletree.events.listeners;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.imamapletree.tools.Utility;

public class HurtHostileMobListener implements Listener{
	public HurtHostileMobListener() {
	}
	
	@EventHandler
	public void onInteract(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Monster)) return;
		if(!(event.getDamager() instanceof Player)) return;
		Player player = ((Player) event.getDamager());
		if(Utility.SimpleACheck("AllowHurtingHostileMobs", player, event.getEntity().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
