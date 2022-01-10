package me.imamapletree.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import me.imamapletree.tools.Utility;

public class PotionThrowListener implements Listener{
	public PotionThrowListener() {
	}
	
	@EventHandler
	public void onInteract(ProjectileLaunchEvent event) {
		if(!(event.getEntity() instanceof ThrownPotion)) return;
		ThrownPotion potion = (ThrownPotion) event.getEntity();
		if(!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) potion.getShooter();
		if(Utility.SimpleACheck("AllowPotionThrow", player, player.getLocation())) return;
		potion.remove();
		return;
	}
}
