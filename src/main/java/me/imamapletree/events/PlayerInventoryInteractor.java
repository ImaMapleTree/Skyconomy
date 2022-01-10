package me.imamapletree.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.SkyconomyInventoryHolder;
import me.imamapletree.executors.IconClickExecutor;
import me.imamapletree.executors.IconRightClickExecutor;
import me.imamapletree.panels.Icon;
import me.imamapletree.panels.InterfacePanel;

public class PlayerInventoryInteractor implements Listener {
	private Skyconomy plugin;
	
	
	public PlayerInventoryInteractor(Skyconomy plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void InvClickEvent(InventoryClickEvent event) {
		if (!(event.getInventory().getHolder() instanceof SkyconomyInventoryHolder)) return;
		event.setCancelled(true);
		final int slotnum = event.getRawSlot();
		final InterfacePanel IP = ((SkyconomyInventoryHolder) event.getInventory().getHolder()).getPanel();
		if (slotnum > IP.getSize()-1) return;
		Icon icon = IP.getIcon(slotnum);
		if (icon == null) return;
		final Player player = (Player) event.getWhoClicked();
		if(icon.hasRightClick() && event.getAction().equals(InventoryAction.PICKUP_HALF)) Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, (Runnable)new IconRightClickExecutor(player, icon));
		else Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, (Runnable)new IconClickExecutor(player, icon));
	}
}
