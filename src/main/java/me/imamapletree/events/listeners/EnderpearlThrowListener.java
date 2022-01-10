package me.imamapletree.events.listeners;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import me.imamapletree.tools.Utility;

public class EnderpearlThrowListener implements Listener{
	public EnderpearlThrowListener() {
	}
	
	@EventHandler
	public void onInteract(ProjectileLaunchEvent event) {
		if(!(event.getEntity() instanceof EnderPearl)) return;
		EnderPearl pearl = (EnderPearl) event.getEntity();
		if(!(event.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) pearl.getShooter();
		if(Utility.SimpleACheck("AllowPotionThrow", player, player.getLocation())) return;
		pearl.remove();
		return;
	}
}
