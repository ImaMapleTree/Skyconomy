package me.imamapletree.events.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import me.imamapletree.tools.Utility;

public class AnimalRideListener implements Listener{
	public AnimalRideListener() {
	}
	
	@EventHandler
	public void onInteract(VehicleEnterEvent event) {
		Entity entity = event.getEntered();
		if(!(entity instanceof Player)) return;
		Player player = ((Player) entity);
		if(Utility.SimpleACheck("AllowAnimalRiding", player, event.getVehicle().getLocation())) return;
		event.setCancelled(true);
		return;
	}
}
