package me.imamapletree.events.listeners.addons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import de.tr7zw.nbtapi.NBTItem;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.Recipe;
import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.executors.CraftExecutor;
import me.imamapletree.executors.WorkbenchSummonExecutor;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.builders.SentenceBuilder;
import org.bukkit.ChatColor;

public class PlaceAutoCraftListener implements Listener{
	private Skyconomy skyconomy;
	private NamespacedKey key;
	private NamespacedKey crafting_key;
	
	public PlaceAutoCraftListener(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		this.key = new NamespacedKey(skyconomy, "CUSTOM_DATA");
		this.crafting_key = new NamespacedKey(skyconomy, "CRAFTING");
	}
	/*
	@EventHandler
	public void onMachineTransfer(MachineOutputEvent event) {
		Container container = (Container) event.getDestination().getLocation().getBlock().getState();
		Inventory inv = container.getInventory();
		if(inv.contains(event.getItem(), 64)) {event.Cancel(); SkyLog.log(inv.contains(event.getItem(), 64)); return;}
		if(inv.firstEmpty() == -1) {event.Cancel(); return;}
		PersistentDataContainer PDC = container.getPersistentDataContainer();
		if(!PDC.has(this.crafting_key, PersistentDataType.STRING)) return;
		String recipe_string = PDC.get(this.crafting_key, PersistentDataType.STRING);
		Recipe recipe = new Recipe(recipe_string);
		container.update();
		Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, (Runnable)new CraftExecutor(recipe, container.getInventory()), 1L);
	}
	*/
	
	@EventHandler
	public void onTransfer(InventoryMoveItemEvent event) {
		Container container = (Container) event.getDestination().getLocation().getBlock().getState();
		if(!container.getPersistentDataContainer().has(crafting_key, PersistentDataType.STRING)) return;
		Inventory inv = container.getInventory();
		//if(inv.contains(event.getItem(), 64)) {event.setCancelled(true); SkyLog.log(inv.contains(event.getItem(), 64)); return;}
		if(inv.firstEmpty() == -1) {event.setCancelled(true); return;}
		PersistentDataContainer PDC = container.getPersistentDataContainer();
		String recipe_string = PDC.get(this.crafting_key, PersistentDataType.STRING);
		Recipe recipe = new Recipe(recipe_string);
		//container.update();
		Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, (Runnable)new CraftExecutor(recipe, container.getInventory()), 2L);
	}
	
	/*@EventHandler
	public void onTransfer(InventoryMoveItemEvent event) {
		if(!(event.getInitiator().getHolder() instanceof Hopper)) return;
		if(!(event.getDestination().getHolder() instanceof Dispenser)) return;
		Dispenser dispenser = (Dispenser) event.getDestination().getLocation().getBlock().getState();
		Inventory inv = dispenser.getInventory();
		if(inv.contains(event.getItem(), 64)) {event.setCancelled(true); SkyLog.log(inv.contains(event.getItem(), 64)); return;}
		if(inv.firstEmpty() == -1) {event.setCancelled(true); return;}
		PersistentDataContainer PDC = dispenser.getPersistentDataContainer();
		if(!PDC.has(this.crafting_key, PersistentDataType.STRING)) return;
		String recipe_string = PDC.get(this.crafting_key, PersistentDataType.STRING);
		Recipe recipe = new Recipe(recipe_string);
		dispenser.update();
		Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, (Runnable)new CraftExecutor(recipe, dispenser.getInventory()), 1L);
	}
	*/
	
	@EventHandler
	public void onArmorStand(PlayerArmorStandManipulateEvent event) {
		if(!event.getRightClicked().getCustomName().equals("AutocraftingTable")) return;
		event.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if(!event.getWhoClicked().hasMetadata("AutoCraftingLoc")) return;
		Player player = (Player) event.getWhoClicked();
		if(event.getInventory().getLocation().getBlock().getType() == Material.CRAFTING_TABLE) {
			player.removeMetadata("AutoCraftingLoc", skyconomy);
			return;
		}
		Recipe recipe = new Recipe(event.getInventory(), event.getRecipe());
		Location loc = WorldCoordinate.createLocation(player.getWorld(), CoordinateParse.WorldCoordParse((String) player.getMetadata("AutoCraftingLoc").get(0).value()));
		player.removeMetadata("AutoCraftingLoc", skyconomy);
		Dispenser dispenser = (Dispenser) loc.getBlock().getState();
		dispenser.getPersistentDataContainer().set(this.crafting_key, PersistentDataType.STRING, recipe.toString());
		dispenser.update();
		event.setCancelled(true);
		player.closeInventory();
		player.sendMessage(new SentenceBuilder(ChatColor.GREEN + "Autocrafting Table now set to make: ", false).add(ChatColor.GOLD + "" + ChatColor.BOLD).add(recipe.getResult().getType().toString()).create());
	}
	
	@EventHandler
	public void onCraftingTable(InventoryOpenEvent event) {
		if(!(event.getInventory().getHolder() instanceof Dispenser)) return;
		Location loc = event.getView().getTopInventory().getLocation();
		Dispenser dispenser = (Dispenser) loc.getBlock().getState();
		if(!dispenser.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
		if(!dispenser.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals("AUTOCRAFTINGTABLE")) return;
		event.setCancelled(true);
		event.getPlayer().setMetadata("AutoCraftingLoc", new FixedMetadataValue(skyconomy, CoordinateParse.toString(new WorldCoordinate(loc))));
		event.getPlayer().openWorkbench(dispenser.getLocation(), true);
		return;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(!(event.getBlock().getState() instanceof Container)) return;
		Container container = (Container) event.getBlock().getState();
		if(!container.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
		Entity entity = container.getWorld().getEntities().stream().filter(e -> event.getBlock().equals(e.getLocation().getBlock())).findFirst().orElse(null);
		//Entity entity = container.getWorld().getEntities().stream().filter(e -> e.getLocation().getBlock().getLocation().equals(event.getBlock().getLocation())).findAny().orElse(null);
		if(entity == null) return;
		entity.remove();
	}
	
	@EventHandler
	public void onInteract(BlockPlaceEvent event) {
		if(event.getBlock().getType() != Material.DISPENSER) return;
		NBTItem nbti = new NBTItem(event.getItemInHand());
		if(!nbti.getString("CUSTOM_DATA").equals("AUTOCRAFTINGTABLE")) return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, (Runnable)new WorkbenchSummonExecutor(skyconomy, event.getBlock()), 1L);
		return;
	}
}
