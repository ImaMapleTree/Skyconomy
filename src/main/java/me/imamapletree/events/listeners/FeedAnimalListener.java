package me.imamapletree.events.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTEntity;
import me.imamapletree.tools.MatCheck;
import me.imamapletree.tools.Utility;

public class FeedAnimalListener implements Listener{
	public FeedAnimalListener() {
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent event) {
		Entity animal = event.getRightClicked();
		if(!(animal instanceof Animals)) return;
		Player player = event.getPlayer();
		if (MatCheck.isBreedable(player.getEquipment().getItemInMainHand().getType()) || MatCheck.isBreedable(player.getEquipment().getItemInOffHand().getType())) {
			if(Utility.SimpleACheck("BreedAnimals", event.getPlayer(), animal.getLocation())) return;
			event.setCancelled(true);
			NBTEntity nbtAnimal = new NBTEntity(animal);
			nbtAnimal.mergeCompound(new NBTContainer("{InLove:1}"));
			return;
		} return;
	}
}
